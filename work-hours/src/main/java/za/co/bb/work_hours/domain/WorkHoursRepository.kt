package za.co.bb.work_hours.domain

import za.co.bb.core.domain.EmployeeId

interface WorkHoursRepository {

    suspend fun getHoursDueForEmployees(employees: List<EmployeeId>): Result<Map<EmployeeId, List<WorkHours>>>
    suspend fun getHoursDueForEmployee(employeeId: String): Result<List<WorkHours>>
}