package za.co.bb.employee.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import co.za.bb.employee.domain.model.Employee
import za.co.bb.employee.presentation.EmployeeScreenState
import za.co.bb.employee.presentation.EmployeeScreenViewModel
import za.co.bb.employee.presentation.EmployeeScreenViewModelFactory
import co.za.bb.navigation.Screen
import co.za.bb.theme.AppColors

fun NavGraphBuilder.employeeListScreen() {
    composable(route = Screen.EMPLOYEE_LIST) {
        val employeeListScreenViewModel = viewModel<EmployeeScreenViewModel>(
            factory = EmployeeScreenViewModelFactory(
                employeeRepository = co.za.bb.employee.di.DependencyContainer.employeeRepository
            )
        )
        val uiState by employeeListScreenViewModel.uiState.collectAsStateWithLifecycle()

        EmployeeListScreen(
            uiState = uiState
        )
    }
}

@Composable
private fun EmployeeListScreen(
    uiState: EmployeeScreenState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(AppColors.current.primary)
        ) {
            Text(
                text = "Employees",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = AppColors.current.onPrimary
                ),
                modifier = Modifier
                    .padding(start = START_PADDING.dp)
                    .align(Alignment.CenterStart)
                    .statusBarsPadding()
            )
        }

        Divider(
            modifier = Modifier.fillMaxWidth()
        )

        EmployeeList(
            modifier = Modifier
                .weight(1f)
                .navigationBarsPadding(),
            employees = uiState.employees
        )
    }
}

@Composable
private fun EmployeeList(
    modifier: Modifier,
    employees: List<Employee>
) {
    LazyColumn(modifier = modifier) {
        items(employees) { employee ->
            Row(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(start = START_PADDING.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = employee.surname,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(", ")
                Text(text = employee.firstName)
            }

            Divider(modifier = Modifier.fillMaxWidth().padding(16.dp))
        }
    }
}

private const val START_PADDING = 16