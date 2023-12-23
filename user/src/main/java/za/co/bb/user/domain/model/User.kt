package za.co.bb.user.domain.model

import za.co.bb.core.domain.UserId

data class User(
    val id: UserId,
    val firstName: String,
    val surname: String,
    val phoneNumber: String,
    val email: String?,
    val userType: UserType
)

enum class UserType {
    Employee,
    Admin;

    companion object {
        fun matchesString(value: String): Boolean = try {
            valueOf(value)
            true
        } catch (t: Throwable) {
            false
        }
    }
}
