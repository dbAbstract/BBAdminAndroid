package za.co.bb.home.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import za.co.bb.core.util.now
import za.co.bb.employees.domain.model.Employee
import za.co.bb.home.domain.model.WageStatus
import za.co.bb.wages.domain.model.Wage

@Composable
internal fun EmployeeWageStatusList(
    modifier: Modifier,
    wageStatusList: List<WageStatus>
) {
    LazyColumn(modifier = modifier) {
        items(wageStatusList) { employeeWageStatus ->
            Row(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(start = START_PADDING.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = employeeWageStatus.employee.surname,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(", ")
                Text(text = employeeWageStatus.employee.firstName)
            }

            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp))
        }
    }
}

@Preview
@Composable
private fun EmployeeWageStatusListPreview() {
    EmployeeWageStatusList(
        modifier = Modifier,
        wageStatusList = listOf(
            WageStatus(
                employee = Employee(
                    id = "0",
                    firstName = "Lionel",
                    surname = "Messi",
                    middleName = null,
                    age = 35
                ),
                wage = Wage(
                    id = "0",
                    employeeId = "0",
                    issueDate = now,
                    amount = 23.0
                ),
                amountDue = 330.50,
                hoursUnpaid = 20
            )
        )
    )
}

private const val START_PADDING = 16