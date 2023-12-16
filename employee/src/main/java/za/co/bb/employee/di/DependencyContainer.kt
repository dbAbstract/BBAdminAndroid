package za.co.bb.employee.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import za.co.bb.employee.data.EmployeeRepositoryImpl
import za.co.bb.employee.domain.EmployeeRepository

object DependencyContainer {
    private val firebaseFirestore by lazy {
        Firebase.firestore
    }

    val employeeRepository: EmployeeRepository by lazy {
        EmployeeRepositoryImpl(firebaseFirestore = firebaseFirestore)
    }
}