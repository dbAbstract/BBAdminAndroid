package za.co.bb.work_hours.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import za.co.bb.work_hours.domain.WorkHoursRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class WorkHoursRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : WorkHoursRepository {
    override suspend fun getHoursDue(employeeId: String): Result<Int> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            firebaseFirestore.collection(WORK_HOURS_TABLE)
                .whereEqualTo(COLUMN_EMPLOYEE_ID, employeeId)
                .get()
                .addOnSuccessListener { result ->
                    result.toObjects(Int::class.java).firstOrNull()?.let {
                        continuation.resume(Result.success(it))
                    } ?: continuation.resume(Result.failure(Exception("Invalid work hours due data.")))
                }
                .addOnCanceledListener {
                    continuation.resume(
                        Result.failure(Throwable(message = "Retrieval of work hours cancelled."))
                    )
                }
                .addOnFailureListener {
                    continuation.resume(Result.failure(it))
                }
        }
    }

    companion object {
        private const val WORK_HOURS_TABLE = "work-hours"
        private const val COLUMN_EMPLOYEE_ID = "employeeId"
    }
}