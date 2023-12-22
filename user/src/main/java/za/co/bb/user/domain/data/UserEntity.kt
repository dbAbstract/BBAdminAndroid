package za.co.bb.user.domain.data

internal data class UserEntity(
    val firstName: String,
    val surname: String,
    val phoneNumber: String,
    val email: String?,
    val userType: String?
)
