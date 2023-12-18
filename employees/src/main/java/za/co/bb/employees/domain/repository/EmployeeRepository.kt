package za.co.bb.employees.domain.repository

import za.co.bb.core.domain.EmployeeId
import za.co.bb.employees.domain.model.Employee

interface EmployeeRepository {
    suspend fun getEmployees(): Result<List<Employee>>
    suspend fun getEmployee(employeeId: EmployeeId): Result<Employee>
}