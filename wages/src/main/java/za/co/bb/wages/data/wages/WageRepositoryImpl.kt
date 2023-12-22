package za.co.bb.wages.data.wages

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
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

    private var lastRefreshed: LocalDateTime? = null
    private val cacheTime: Long = 60 * 5 // 5 Minutes

    private val isCacheValid: Boolean
        get() = lastRefreshed?.let {
            (toEpochSeconds(now) - toEpochSeconds(it) < cacheTime)
        } ?: false

    override suspend fun getWages(): Result<List<Wage>> = withContext(Dispatchers.IO) {
        return@withContext when {
            isCacheValid -> try {
                Result.success(
                    getAllWagesFromFirestore(
                        source = Source.CACHE
                    ).getOrThrow()
                )
            } catch (t: Throwable) {
                Result.failure(t)
            }

            else -> {
                val wages = getAllWagesFromFirestore(source = Source.SERVER)
                try {
                    Result.success(wages.getOrThrow())
                } catch (t: Throwable) {
                    Result.failure(t)
                }
            }
        }
    }

    private suspend fun getAllWagesFromFirestore(source: Source): Result<List<Wage>> = suspendCoroutine { continuation ->
        firebaseFirestore.collection(WAGE_TABLE)
            .get(source)
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
                lastRefreshed = now
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

    companion object {
        private const val WAGE_TABLE = "wage"
    }

}