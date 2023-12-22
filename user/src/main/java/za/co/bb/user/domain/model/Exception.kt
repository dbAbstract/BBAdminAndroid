package za.co.bb.user.domain.model

class NotLoggedInException : Exception("Currently not logged in!")
class InvalidUserFoundException : Exception("The information found for this user is invalid!")