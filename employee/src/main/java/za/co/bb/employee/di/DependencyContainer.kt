package za.co.bb.employee.di

import za.co.bb.employee.domain.EmployeeRepository
import za.co.bb.employee.domain.EmployeeRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

object DependencyContainer {
    private val firebaseFirestore by lazy {
        Firebase.firestore
    }

    val employeeRepository: EmployeeRepository by lazy {
        EmployeeRepositoryImpl(firebaseFirestore = firebaseFirestore)
    }
}