package za.co.bb.wages.domain.repository

import za.co.bb.wages.domain.model.Wage

interface WageRepository {
    suspend fun getWages(): Result<List<Wage>>
}