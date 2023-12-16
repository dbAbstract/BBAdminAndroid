package za.co.bb.employee.domain

import za.co.bb.employee.domain.model.Employee

interface EmployeeRepository {
    suspend fun getEmployees(): Result<List<Employee>>
}