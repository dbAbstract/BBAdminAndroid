package za.co.bb.employees.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import za.co.bb.core.util.now
import za.co.bb.core.util.toEpochSeconds
import za.co.bb.employees.domain.model.Employee
import za.co.bb.employees.domain.repository.EmployeeRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class EmployeeRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : EmployeeRepository {

    private var employeeCache = emptyList<Employee>()
    private var lastRefreshed: LocalDateTime? = null
    private val cacheTime: Long = 60 * 5 // 5 Minutes

    override suspend fun getEmployees(): Result<List<Employee>> = withContext(Dispatchers.IO) {
        val isCacheValid = lastRefreshed?.let {
            (toEpochSeconds(now) - toEpochSeconds(it) < cacheTime) && employeeCache.isNotEmpty()
        } ?: false

        return@withContext when {
            isCacheValid -> {
                Log.i(TAG, "Getting employees from cache.")
                Result.success(employeeCache)
            }

            else -> {
                Log.i(TAG, "Getting employees from remote.")
                getEmployeesFromFirestore()
            }
        }
    }

    private suspend fun getEmployeesFromFirestore(): Result<List<Employee>> = suspendCoroutine { continuation ->
        firebaseFirestore.collection(EMPLOYEE_TABLE).get()
            .addOnSuccessListener { result ->
                try {
                    val employeeSet = mutableSetOf<Employee>()
                    for (document in result) {
                        val employeeId = document.id
                        val entity = document.toObject(EmployeeEntity::class.java)
                        val employee = entity.toEmployee(employeeId = employeeId)
                        employee?.let { employeeSet.add(it) }
                    }
                    cacheEmployeeList(employeeList = employeeSet)
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

    private fun cacheEmployeeList(employeeList: Collection<Employee>) {
        employeeCache = employeeList.toList()
        lastRefreshed = now
    }

    companion object {
        private const val EMPLOYEE_TABLE = "employee"
        private const val TAG = "Employee-Repository"
    }
}