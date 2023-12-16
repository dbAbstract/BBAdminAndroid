package za.co.bb.employees.domain.repository

import za.co.bb.employees.domain.model.Employee

interface EmployeeRepository {
    suspend fun getEmployees(): Result<List<Employee>>
}