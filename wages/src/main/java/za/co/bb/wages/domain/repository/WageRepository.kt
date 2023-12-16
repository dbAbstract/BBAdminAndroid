package za.co.bb.wages.domain.repository

import za.co.bb.wages.domain.model.Wage

interface WageRepository {
    suspend fun getCurrentForEmployee(employeeId: String): Result<Wage>

    suspend fun getWageHistoryForEmployee(employeeId: String): Result<List<Wage>>
}