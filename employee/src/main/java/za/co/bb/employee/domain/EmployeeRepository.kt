package za.co.bb.employee.domain

import com.google.firebase.firestore.FirebaseFirestore
import za.co.bb.employee.domain.model.Employee
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface EmployeeRepository {
    suspend fun getEmployees(): Result<List<Employee>>
}

internal class EmployeeRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : EmployeeRepository {

    override suspend fun getEmployees(): Result<List<Employee>> = suspendCoroutine { continuation ->
        firebaseFirestore.collection(EMPLOYEE_TABLE).get()
            .addOnSuccessListener { result ->
                continuation.resume(
                    Result.success(
                        result.documents.mapNotNull { it.toObject(Employee::class.java) }
                    )
                )
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
    }
}