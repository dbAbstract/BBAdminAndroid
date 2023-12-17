package za.co.bb.wages.domain.model

class NoWagesFoundException : Exception("No wages found for user.")
class InvalidWageDataException : Exception("Invalid data in Wage.")