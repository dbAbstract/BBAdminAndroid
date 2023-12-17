package za.co.bb.employees.domain.model

import za.co.bb.core.domain.EmployeeId

class EmployeeNotFoundException(val employeeId: EmployeeId) : Exception("Employee with id=$employeeId not found.")