package za.co.bb.user.domain.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import za.co.bb.user.domain.UserRepository
import za.co.bb.user.domain.data.UserRepositoryImpl

object UserDependencyContainer {
    private val firebaseAuth by lazy {
        Firebase.auth
    }
    private val firebaseFirestore by lazy {
        Firebase.firestore
    }

    val userRepository: UserRepository by lazy {
        UserRepositoryImpl(
            firebaseAuth = firebaseAuth,
            firebaseFirestore = firebaseFirestore
        )
    }
}