package za.co.bb.employees.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import za.co.bb.employees.data.EmployeeRepositoryImpl
import za.co.bb.employees.domain.repository.EmployeeRepository

object EmployeeDependencyContainer {
    private val firebaseFirestore by lazy {
        Firebase.firestore
    }

    val employeeRepository: EmployeeRepository by lazy {
        EmployeeRepositoryImpl(firebaseFirestore = firebaseFirestore)
    }
}