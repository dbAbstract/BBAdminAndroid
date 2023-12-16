package za.co.bb.wages.data.wages

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import za.co.bb.wages.data.wages.entity.WageEntity
import za.co.bb.wages.data.wages.entity.toWage
import za.co.bb.wages.domain.model.Wage
import za.co.bb.wages.domain.repository.WageRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WageRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : WageRepository {
    override suspend fun getCurrentForEmployee(employeeId: String): Result<Wage> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            firebaseFirestore.collection(WAGE_TABLE)
                .whereEqualTo(COLUMN_EMPLOYEE_ID, employeeId)
                .get()
                .addOnSuccessListener { result ->
                    val wageList = result.toObjects(WageEntity::class.java)
                    wageList.firstOrNull()?.let {
                        val wage = it.toWage()
                        if (wage == null) {
                            continuation.resume(Result.failure(Exception("Error with retrieved Wage.")))
                        } else {
                            continuation.resume(Result.success(wage))
                        }
                    }
                }
                .addOnCanceledListener {
                    continuation.resume(
                        Result.failure(Throwable(message = "Retrieval of wage cancelled."))
                    )
                }
                .addOnFailureListener {
                    continuation.resume(Result.failure(it))
                }
        }
    }

    override suspend fun getWageHistoryForEmployee(employeeId: String): Result<List<Wage>> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            firebaseFirestore.collection(WAGE_TABLE)
                .whereEqualTo(COLUMN_EMPLOYEE_ID, employeeId)
                .get()
                .addOnSuccessListener { result ->
                    val wageList = result
                        .toObjects(WageEntity::class.java)
                        .mapNotNull { it.toWage() }

                    continuation.resume(Result.success(wageList))
                }
                .addOnCanceledListener {
                    continuation.resume(
                        Result.failure(Throwable(message = "Retrieval of wage cancelled."))
                    )
                }
                .addOnFailureListener {
                    continuation.resume(Result.failure(it))
                }
        }
    }

    companion object {
        private const val WAGE_TABLE = "wages"
        private const val COLUMN_EMPLOYEE_ID = "employeeId"
    }

}