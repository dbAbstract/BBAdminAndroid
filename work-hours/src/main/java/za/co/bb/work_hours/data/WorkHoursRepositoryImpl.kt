package za.co.bb.work_hours.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import za.co.bb.core.util.now
import za.co.bb.core.util.toEpochSeconds
import za.co.bb.work_hours.domain.NoWorkHoursFoundException
import za.co.bb.work_hours.domain.WorkHours
import za.co.bb.work_hours.domain.WorkHoursRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class WorkHoursRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : WorkHoursRepository {
    private val workHistoryCache = mutableMapOf<EmployeeId, List<WorkHours>>()
    private var lastRefreshed: LocalDateTime? = null
    private val cacheTime: Long = 60 * 5 // 5 Minutes

    override suspend fun getHoursDueForEmployee(employeeId: String): Result<List<WorkHours>> = withContext(Dispatchers.IO) {
        val isCacheValid = lastRefreshed?.let {
            (toEpochSeconds(now) - toEpochSeconds(it) < cacheTime)
                    && workHistoryCache.isNotEmpty()
                    && workHistoryCache[employeeId]?.isNotEmpty() == true
        } ?: false

        return@withContext when {
            isCacheValid -> {
                Log.i(TAG, "Retrieving work hours for employeeId=$employeeId from cache.")
                Result.success(workHistoryCache[employeeId]!!)
            }

            else -> {
                Log.i(TAG, "Retrieving work hours for employeeId=$employeeId from remote.")
                getWorkHoursFromFirestore(employeeId)
            }
        }
    }

    private suspend fun getWorkHoursFromFirestore(employeeId: EmployeeId): Result<List<WorkHours>> = suspendCoroutine { continuation ->
        Log.i(TAG, "Getting work hours for $employeeId")
        firebaseFirestore.collection(WORK_HOURS_TABLE)
            .whereEqualTo(COLUMN_EMPLOYEE_ID, employeeId)
            .get()
            .addOnSuccessListener { result ->
                try {
                    if (result.isEmpty) {
                        continuation.resume(Result.failure(NoWorkHoursFoundException()))
                        Log.i(TAG, "Empty result set for empId=$employeeId")
                    }

                    val workHourSet = mutableSetOf<WorkHours>()
                    for (document in result) {
                        val workHourId = document.id
                        val workHourEntity = document.toObject<WorkHoursEntity>()
                        val workHours = workHourEntity.toWorkHours(workHoursId = workHourId)
                        workHours?.let {
                            Log.i(TAG, "Found WorkHours $it")
                            workHourSet.add(it)
                        }
                    }
                    continuation.resume(Result.success(workHourSet.toList()))
                } catch (t: Throwable) {
                    Log.e(TAG, "Error getting WorkHours for $employeeId | error=$t")
                    continuation.resume(Result.failure(t))
                }
            }
            .addOnCanceledListener {
                Log.d("lol", "Error getting work hours for $employeeId - Cancelled")
                continuation.resume(
                    Result.failure(Throwable(message = "Retrieval of work hours cancelled."))
                )
            }
            .addOnFailureListener {
                Log.d("lol", "Error getting work hours for $employeeId - Failed")
                continuation.resume(Result.failure(it))
            }
    }


    companion object {
        private const val WORK_HOURS_TABLE = "work-hours"
        private const val COLUMN_EMPLOYEE_ID = "employeeId"
        private const val TAG = "WorkHours-Repository"
    }
}

internal typealias EmployeeId = String