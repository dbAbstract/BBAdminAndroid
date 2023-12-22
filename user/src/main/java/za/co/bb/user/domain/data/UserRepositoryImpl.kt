package za.co.bb.user.domain.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import za.co.bb.core.domain.UserId
import za.co.bb.user.domain.UserRepository
import za.co.bb.user.domain.model.InvalidUserFoundException
import za.co.bb.user.domain.model.NotLoggedInException
import za.co.bb.user.domain.model.User
import za.co.bb.user.domain.model.UserType
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class UserRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : UserRepository {
    override suspend fun getCurrentUser(): Result<User> = withContext(Dispatchers.IO) {
        val currentUser = firebaseAuth.currentUser ?: return@withContext Result.failure(NotLoggedInException())

        return@withContext suspendCoroutine { continuation ->
            firebaseFirestore.collection(TABLE_USERS).document(currentUser.uid).get()
                .addOnSuccessListener {
                    val userEntity = it.toObject<UserEntity>()
                    val userEntityTypeString = userEntity?.userType

                    if (userEntity == null || userEntityTypeString == null) {
                        continuation.resume(Result.failure(InvalidUserFoundException()))
                        return@addOnSuccessListener
                    }

                    val userType = try {
                        UserType.valueOf(userEntityTypeString)
                    } catch (t: Throwable) {
                        continuation.resume(Result.failure(t))
                        return@addOnSuccessListener
                    }

                    val user = User(
                        id = currentUser.uid,
                        firstName = userEntity.firstName,
                        surname = userEntity.surname,
                        phoneNumber = userEntity.phoneNumber,
                        email = userEntity.email,
                        userType = userType
                    )

                    continuation.resume(Result.success(user))
                }
        }
    }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Result<UserId> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it.user?.uid?.let { userId ->
                        continuation.resume(Result.success(userId))
                    } ?: continuation.resume(Result.failure(InvalidUserFoundException()))
                }
                .addOnFailureListener {
                    continuation.resume(Result.failure(it))
                }
                .addOnCanceledListener {
                    continuation.resume(Result.failure(CancellationException()))
                }
        }
    }

    internal companion object {
        private const val TABLE_USERS = "user"
    }
}