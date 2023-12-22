package za.co.bb.work_hours.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import za.co.bb.core.domain.EmployeeId
import za.co.bb.core.domain.Rand
import za.co.bb.core.domain.WageId
import za.co.bb.core.domain.WorkHoursId
import za.co.bb.core.util.now
import za.co.bb.core.util.toEpochSeconds
import za.co.bb.work_hours.domain.DeleteWorkHoursFailureException
import za.co.bb.work_hours.domain.WorkHours
import za.co.bb.work_hours.domain.WorkHoursRepository
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class WorkHoursRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : WorkHoursRepository {
    private var lastRefreshed: LocalDateTime? = null
    private val cacheTime: Long = 60 * 5 // 5 Minutes

    private val isCacheValid
        get() = lastRefreshed?.let {
            (toEpochSeconds(now) - toEpochSeconds(it) < cacheTime)
        } ?: false

    override suspend fun getHoursDueForEmployees(employees: List<EmployeeId>) = try {
         Result.success(
             employees.associateWith { getHoursDueForEmployee(it).getOrThrow() }
         ).also {
             lastRefreshed = now
         }
    } catch (t: Throwable) {
        Result.failure(t)
    }

    override suspend fun getHoursDueForEmployee(employeeId: String): Result<List<WorkHours>> = withContext(Dispatchers.IO) {
        return@withContext when {
            isCacheValid -> {
                Log.i(TAG, "Retrieving work hours for employeeId=$employeeId from cache.")
                try {
                    val workHoursResult = getWorkHoursFromFirestore(
                        employeeId = employeeId,
                        source = Source.CACHE
                    )
                    Result.success(workHoursResult.getOrThrow())
                } catch (t: Throwable) {
                    Result.failure(t)
                }
            }

            else -> {
                Log.i(TAG, "Retrieving work hours for employeeId=$employeeId from remote.")
                getWorkHoursFromFirestore(employeeId, Source.SERVER)
            }
        }
    }

    override suspend fun deleteWorkHourItems(workHoursIdList: List<WorkHoursId>): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext if (workHoursIdList.all { deleteWorkHour(it).isSuccess })
            Result.success(Unit)
        else
            Result.failure(DeleteWorkHoursFailureException())
    }

    override suspend fun addWorkHourForEmployee(
        employeeId: EmployeeId,
        hoursWorked: Long,
        wageId: WageId,
        wageRate: Rand
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val workHoursEntity = WorkHoursEntity(
            employeeId = employeeId,
            hoursDue = hoursWorked,
            wageId = wageId,
            wageRate = wageRate,
            creationDate = toEpochSeconds(now)
        )

        suspendCoroutine { continuation ->
            firebaseFirestore.collection(WORK_HOURS_TABLE).add(workHoursEntity)
                .addOnSuccessListener {
                    continuation.resume(Result.success(Unit))
                }
                .addOnFailureListener {
                    continuation.resume(Result.failure(it))
                }
                .addOnCanceledListener {
                    continuation.resume(Result.failure(CancellationException()))
                }
        }

    }

    private suspend fun deleteWorkHour(workHoursId: WorkHoursId): Result<Unit> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            firebaseFirestore.collection(WORK_HOURS_TABLE)
                .document(workHoursId)
                .delete()
                .addOnSuccessListener {
                    continuation.resume(Result.success(Unit))
                }
                .addOnFailureListener {
                    continuation.resume(Result.failure(it))
                }
        }
    }

    private suspend fun getWorkHoursFromFirestore(
        employeeId: EmployeeId,
        source: Source
    ): Result<List<WorkHours>> = suspendCoroutine { continuation ->
        Log.i(TAG, "Getting work hours for $employeeId")
        firebaseFirestore.collection(WORK_HOURS_TABLE)
            .whereEqualTo(COLUMN_EMPLOYEE_ID, employeeId)
            .get(source)
            .addOnSuccessListener { result ->
                try {
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
                continuation.resume(
                    Result.failure(Throwable(message = "Retrieval of work hours cancelled."))
                )
            }
            .addOnFailureListener {
                continuation.resume(Result.failure(it))
            }
    }

    companion object {
        private const val WORK_HOURS_TABLE = "work-hours"
        private const val COLUMN_EMPLOYEE_ID = "employeeId"
        private const val TAG = "Work-Hours-Repository"
    }
}