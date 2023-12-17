package za.co.bb.wages.data.wages

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import za.co.bb.core.util.now
import za.co.bb.core.util.toEpochSeconds
import za.co.bb.wages.data.wages.entity.WageEntity
import za.co.bb.wages.data.wages.entity.toWage
import za.co.bb.wages.domain.model.NoWagesFoundException
import za.co.bb.wages.domain.model.Wage
import za.co.bb.wages.domain.repository.WageRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WageRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore
) : WageRepository {

    private val wageHistoryCache = mutableMapOf<EmployeeId, List<Wage>>()
    private var lastRefreshed: LocalDateTime? = null
    private val cacheTime: Long = 60 * 5 // 5 Minutes

    override suspend fun getCurrentWageForEmployee(employeeId: String): Result<Wage> = withContext(Dispatchers.IO) {
        val isCacheValid = lastRefreshed?.let {
            (toEpochSeconds(now) - toEpochSeconds(it) < cacheTime)
                    && wageHistoryCache.isNotEmpty()
                    && wageHistoryCache[employeeId]?.isNotEmpty() == true
        } ?: false

        return@withContext when {
            isCacheValid -> Result.success(wageHistoryCache[employeeId]!!.last())

            else -> {
                val wages = getAllWagesFromFirestore(employeeId)
                try {
                    Result.success(wages.getOrThrow().last())
                } catch (t: Throwable) {
                    Result.failure(t)
                }
            }
        }
    }

    override suspend fun getWageHistoryForEmployee(employeeId: String): Result<List<Wage>> = withContext(Dispatchers.IO) {
        val isCacheValid = lastRefreshed?.let {
            (toEpochSeconds(now) - toEpochSeconds(it) < cacheTime)
                    && wageHistoryCache.isNotEmpty()
                    && wageHistoryCache[employeeId]?.isNotEmpty() == true
        } ?: false

        return@withContext when {
            isCacheValid -> {
                Result.success(wageHistoryCache[employeeId]!!)
            }

            else -> {
                getAllWagesFromFirestore(employeeId = employeeId)
            }
        }
    }

    private suspend fun getAllWagesFromFirestore(employeeId: String): Result<List<Wage>> = suspendCoroutine { continuation ->
        firebaseFirestore.collection(WAGE_TABLE)
            .whereEqualTo(COLUMN_EMPLOYEE_ID, employeeId)
            .orderBy(COLUMN_ISSUE_DATE, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    continuation.resume(Result.failure(NoWagesFoundException()))
                }

                val wageSet = mutableSetOf<Wage>()
                for (document in result) {
                    val wageId = document.id
                    val wageEntity = document.toObject<WageEntity>()
                    val wage = wageEntity.toWage(wageId = wageId)
                    wage?.let {
                        wageSet.add(wage)
                    }
                }
                cacheWages(
                    employeeId = employeeId,
                    wages = wageSet.toList()
                )
                continuation.resume(Result.success(wageSet.toList()))
            }
            .addOnCanceledListener {
                continuation.resume(
                    Result.failure(Throwable(message = "Retrieval of wage cancelled."))
                )
            }
            .addOnFailureListener {
                continuation.resume(Result.failure(it))
            }
    }

    private fun cacheWages(
        employeeId: EmployeeId,
        wages: List<Wage>
    ) {
        wageHistoryCache[employeeId] = wages
        lastRefreshed = now
    }

    companion object {
        private const val WAGE_TABLE = "wage"
        private const val COLUMN_EMPLOYEE_ID = "employeeId"
        private const val COLUMN_ISSUE_DATE = "issueDate"
    }

}

internal typealias EmployeeId = String