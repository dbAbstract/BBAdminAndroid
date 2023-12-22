package za.co.bb.user.domain

import za.co.bb.core.domain.UserId
import za.co.bb.user.domain.model.User

interface UserRepository {
    suspend fun getCurrentUser(): Result<User>

    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Result<UserId>
}