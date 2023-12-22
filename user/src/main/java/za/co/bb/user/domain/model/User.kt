package za.co.bb.user.domain.model

data class User(
    val firstName: String,
    val surname: String,
    val phoneNumber: String,
    val email: String?,
    val userType: UserType
)

enum class UserType {
    Employee,
    Admin
}
