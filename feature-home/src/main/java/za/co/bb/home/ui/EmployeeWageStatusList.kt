package za.co.bb.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import za.co.bb.core.domain.EmployeeId
import za.co.bb.employees.domain.model.Employee
import za.co.bb.home.R
import za.co.bb.home.domain.model.WageStatus

@Composable
internal fun EmployeeWageStatusList(
    modifier: Modifier,
    wageStatusList: List<WageStatus>,
    onWageStatusClick: (EmployeeId) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(wageStatusList) { employeeWageStatus ->
            Row(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .clickable { onWageStatusClick(employeeWageStatus.employee.id) }
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

                Spacer(modifier = Modifier.weight(1f))

                Column(modifier = Modifier.padding(end = 8.dp)) {
                    Text(
                        text = stringResource(id = R.string.hours_due),
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(text = employeeWageStatus.hoursUnpaid.toString())
                }
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
        modifier = Modifier.background(Color.White),
        wageStatusList = previewWageStatusList,
        onWageStatusClick = {}
    )
}

private const val START_PADDING = 16

private val previewWageStatusList = listOf(
    WageStatus(
        employee = Employee(
            id = "0",
            firstName = "Lionel",
            surname = "Messi",
            middleName = null,
            age = 35
        ),
        amountDue = 330.50,
        hoursUnpaid = 20
    ),
    WageStatus(
        employee = Employee(
            id = "1",
            firstName = "Neymar",
            surname = "Jr",
            middleName = null,
            age = 31
        ),
        amountDue = 310.50,
        hoursUnpaid = 32
    )
)