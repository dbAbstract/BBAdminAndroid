package za.co.bb.work_hours.domain

import za.co.bb.core.domain.EmployeeId
import za.co.bb.core.domain.WorkHoursId

interface WorkHoursRepository {

    suspend fun getHoursDueForEmployees(employees: List<EmployeeId>): Result<Map<EmployeeId, List<WorkHours>>>

    suspend fun getHoursDueForEmployee(employeeId: String): Result<List<WorkHours>>

    suspend fun deleteWorkHourItems(workHoursIdList: List<WorkHoursId>): Result<Unit>
}