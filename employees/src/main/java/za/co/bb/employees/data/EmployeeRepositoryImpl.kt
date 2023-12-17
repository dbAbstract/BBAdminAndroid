package za.co.bb.employees.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import za.co.bb.employees.domain.model.Employee
import za.co.bb.employees.domain.repository.EmployeeRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class EmployeeRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : EmployeeRepository {

    override suspend fun getEmployees(): Result<List<Employee>> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
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
    }

    companion object {
        private const val EMPLOYEE_TABLE = "employee"
    }
}