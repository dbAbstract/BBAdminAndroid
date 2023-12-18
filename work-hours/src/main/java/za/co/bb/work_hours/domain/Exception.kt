package za.co.bb.work_hours.domain

class NoWorkHoursFoundException : Exception("No work hour found.")
class DeleteWorkHoursFailureException : Exception("Failed to delete all work hours.")