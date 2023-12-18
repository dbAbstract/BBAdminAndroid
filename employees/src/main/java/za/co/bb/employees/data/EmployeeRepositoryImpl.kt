package za.co.bb.employees.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import za.co.bb.core.domain.EmployeeId
import za.co.bb.core.util.now
import za.co.bb.core.util.toEpochSeconds
import za.co.bb.employees.domain.model.Employee
import za.co.bb.employees.domain.model.EmployeeNotFoundException
import za.co.bb.employees.domain.repository.EmployeeRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class EmployeeRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : EmployeeRepository {

    private var lastRefreshed: LocalDateTime? = null
    private val cacheTime: Long = 60 * 5 // 5 Minutes

    private val isCacheValid: Boolean
        get() = lastRefreshed?.let {
            (toEpochSeconds(now) - toEpochSeconds(it) < cacheTime)
        } ?: false

    override suspend fun getEmployees(): Result<List<Employee>> = withContext(Dispatchers.IO) {
        return@withContext when {
            isCacheValid -> {
                Log.i(TAG, "Getting employees from cache.")
                val employeesResult = getEmployeesFromFirestore(Source.CACHE)
                try {
                    Result.success(employeesResult.getOrThrow())
                } catch (t: Throwable) {
                    Result.failure(t)
                }
            }

            else -> {
                Log.i(TAG, "Getting employees from remote.")
                getEmployeesFromFirestore(Source.SERVER)
            }
        }
    }

    override suspend fun getEmployee(employeeId: EmployeeId): Result<Employee> = withContext(Dispatchers.IO) {
        return@withContext when {
            isCacheValid -> {
                Log.i(TAG, "Getting employee with id=$employeeId from cache.")
                val employeesResult = getEmployeesFromFirestore(Source.CACHE)
                try {
                    val employeeFromCache = employeesResult.getOrThrow().find { it.id == employeeId }

                    if (employeeFromCache != null) {
                        Result.success(employeeFromCache)
                    } else {
                        val employeeFromRemote = getEmployeesFromFirestore(Source.SERVER)
                            .getOrThrow()
                            .find { it.id == employeeId }

                        employeeFromRemote?.let {
                            Result.success(it)
                        } ?: Result.failure(EmployeeNotFoundException(employeeId))
                    }
                } catch (t: Throwable) {
                    Result.failure(t)
                }
            }

            else -> {
                Log.i(TAG, "Getting employee with id=$employeeId from remote.")
                try {
                    val employees = getEmployeesFromFirestore(Source.SERVER).getOrThrow()
                    val employee = employees.find { it.id == employeeId }
                    if (employee == null) {
                        Result.failure(EmployeeNotFoundException(employeeId))
                    } else {
                        Result.success(employee)
                    }
                } catch (t: Throwable) {
                    Result.failure(t)
                }
            }
        }
    }

    private suspend fun getEmployeesFromFirestore(source: Source): Result<List<Employee>> = suspendCoroutine { continuation ->
        firebaseFirestore.collection(EMPLOYEE_TABLE).get(source)
            .addOnSuccessListener { result ->
                try {
                    val employeeSet = mutableSetOf<Employee>()
                    for (document in result) {
                        val employeeId = document.id
                        val entity = document.toObject(EmployeeEntity::class.java)
                        val employee = entity.toEmployee(employeeId = employeeId)
                        employee?.let { employeeSet.add(it) }
                    }
                    lastRefreshed = now
                    continuation.resume(
                        Result.success(employeeSet.toList())
                    )
                } catch (t: Throwable) {
                    continuation.resume(Result.failure(t))
                }
            }
            .addOnCanceledListener {
                continuation.resume(
                    Result.failure(Throwable(message = "Retrieval of employees cancelled."))
                )
            }
            .addOnFailureListener {
                continuation.resume(Result.failure(it))
            }
    }

    companion object {
        private const val EMPLOYEE_TABLE = "employee"
        private const val TAG = "Employee-Repository"
    }
}