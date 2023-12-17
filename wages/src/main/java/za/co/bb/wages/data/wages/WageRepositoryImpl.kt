package za.co.bb.wages.data.wages

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import za.co.bb.wages.data.wages.entity.WageEntity
import za.co.bb.wages.data.wages.entity.toWage
import za.co.bb.wages.domain.model.InvalidWageDataException
import za.co.bb.wages.domain.model.NoWagesFoundException
import za.co.bb.wages.domain.model.Wage
import za.co.bb.wages.domain.repository.WageRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WageRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : WageRepository {
    override suspend fun getCurrentWageForEmployee(employeeId: String): Result<Wage> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            firebaseFirestore.collection(WAGE_TABLE)
                .whereEqualTo(COLUMN_EMPLOYEE_ID, employeeId)
                .get()
                .addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        continuation.resume(Result.failure(NoWagesFoundException()))
                    }
                    val document = result.first()

                    try {
                        val wageId = document.id
                        val wageEntity = document.toObject<WageEntity>()
                        val wage = wageEntity.toWage(wageId = wageId)
                        wage?.let {
                            continuation.resume(Result.success(it))
                        } ?: continuation.resume(Result.failure(InvalidWageDataException()))

                    } catch (t: Throwable) {
                        continuation.resume(Result.failure(t))
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
                    if (result.isEmpty) {
                        continuation.resume(Result.failure(NoWagesFoundException()))
                    }

                    val wageSet = mutableSetOf<Wage>()
                    for (document in result) {
                        val wageId = document.id
                        val wageEntity = document.toObject<WageEntity>()
                        val wage = wageEntity.toWage(wageId = wageId)
                        wage?.let {
                            wageSet.add(wage)
                        }
                    }

                    continuation.resume(Result.success(wageSet.toList()))
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
        private const val WAGE_TABLE = "wage"
        private const val COLUMN_EMPLOYEE_ID = "employeeId"
    }

}