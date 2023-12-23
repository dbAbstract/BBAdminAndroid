package za.co.bb.user.domain.data

import za.co.bb.core.domain.UserId
import za.co.bb.user.domain.model.User
import za.co.bb.user.domain.model.UserType

internal data class UserEntity(
    val firstName: String? = null,
    val surname: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val userType: String? = null
)

internal fun UserEntity.toUser(userId: UserId): User? {
    return if (firstName != null &&
            surname != null &&
            phoneNumber != null &&
            userType != null &&
            UserType.matchesString(userType)
        ) {
        User(
            id = userId,
            firstName = firstName,
            surname = surname,
            phoneNumber = phoneNumber,
            email = email,
            userType = UserType.valueOf(userType)
        )
    } else null
}
