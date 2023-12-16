package za.co.bb.employee.di

import za.co.bb.core.di.CoreDependencyContainer
import za.co.bb.employee.data.EmployeeRepositoryImpl
import za.co.bb.employee.domain.EmployeeRepository

object DependencyContainer {
    val employeeRepository: EmployeeRepository by lazy {
        EmployeeRepositoryImpl(firebaseFirestore = CoreDependencyContainer.firebaseFirestore)
    }
}