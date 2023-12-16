package za.co.bb.employees.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import za.co.bb.employees.domain.model.Employee
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class EmployeeRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : za.co.bb.employees.domain.repository.EmployeeRepository {

    override suspend fun getEmployees(): Result<List<Employee>> = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            firebaseFirestore.collection(EMPLOYEE_TABLE).get()
                .addOnSuccessListener { result ->
                    continuation.resume(
                        Result.success(
                            result.toObjects(EmployeeEntity::class.java).mapNotNull {
                                it.toEmployee()
                            }
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
    }

    companion object {
        private const val EMPLOYEE_TABLE = "employee"
    }
}