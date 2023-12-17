package za.co.bb.work_hours.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import za.co.bb.work_hours.domain.NoWorkHoursFoundException
import za.co.bb.work_hours.domain.WorkHours
import za.co.bb.work_hours.domain.WorkHoursRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class WorkHoursRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : WorkHoursRepository {
    override suspend fun getHoursDueForEmployee(employeeId: String): Result<List<WorkHours>> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            firebaseFirestore.collection(WORK_HOURS_TABLE)
                .whereEqualTo(COLUMN_EMPLOYEE_ID, employeeId)
                .get()
                .addOnSuccessListener { result ->
                    try {
                        if (result.isEmpty) {
                            continuation.resume(Result.failure(NoWorkHoursFoundException()))
                        }

                        val workHourSet = mutableSetOf<WorkHours>()
                        for (document in result) {
                            val workHourId = document.id
                            val workHourEntity = document.toObject<WorkHoursEntity>()
                            val workHours = workHourEntity.toWorkHours(workHoursId = workHourId)
                            workHours?.let {
                                workHourSet.add(it)
                            }
                        }
                        continuation.resume(Result.success(workHourSet.toList()))
                    } catch (t: Throwable) {
                        continuation.resume(Result.failure(t))
                    }
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