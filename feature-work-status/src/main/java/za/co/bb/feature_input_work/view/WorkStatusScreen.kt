package za.co.bb.feature_input_work.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.components.AppTopBar
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.core.util.collectAction
import za.co.bb.feature_input_work.presentation.WorkStatusAction
import za.co.bb.feature_input_work.presentation.WorkStatusEventHandler
import za.co.bb.feature_input_work.presentation.WorkStatusScreenState
import za.co.bb.feature_input_work.view.ui.EmployeeDetailsRow
import za.co.bb.feature_input_work.view.ui.EmployeeWorkStatusFeed
import za.co.bb.feature_input_work.view.ui.WorkStatusScreenError
import za.co.bb.feature_input_work.view.ui.WorkStatusScreenLoading
import za.co.bb.feature_work_status.R

fun NavGraphBuilder.workStatusScreen(
    navigate: (NavAction) -> Unit
) {
    composable(
        route = "${Screen.WorkStatus.name}/{$ARG_EMPLOYEE_ID}",
        arguments = listOf(
            navArgument(ARG_EMPLOYEE_ID) {
                type = NavType.StringType
            }
        ),
    ) { backStackEntry ->
        val employeeId = backStackEntry.arguments?.getString(ARG_EMPLOYEE_ID)
        if (employeeId == null) {
            navigate(NavAction.NavigateBack)
            return@composable
        }

        val workStatusViewModel = getWorkStatusViewModel(employeeId = employeeId)
        val uiState by workStatusViewModel.uiState.collectAsStateWithLifecycle()

        WorkStatusScreen(
            uiState = uiState,
            workStatusEventHandler = workStatusViewModel.workStatusEventHandler
        )

        workStatusViewModel.collectAction { action ->
            when (action) {
                WorkStatusAction.NavigateBack -> navigate(NavAction.NavigateBack)
            }
        }
    }
}

private const val ARG_EMPLOYEE_ID = "employeeId"

@Composable
private fun WorkStatusScreen(
    uiState: WorkStatusScreenState,
    workStatusEventHandler: WorkStatusEventHandler
) {
    when (uiState) {
        WorkStatusScreenState.Error -> {
            WorkStatusScreenError(onBack = workStatusEventHandler::onBack)
        }

        WorkStatusScreenState.Loading -> {
            WorkStatusScreenLoading(onBack = workStatusEventHandler::onBack)
        }

        is WorkStatusScreenState.Loaded -> {
            WorkStatusScreen(
                uiState = uiState,
                workStatusEventHandler = workStatusEventHandler
            )
        }
    }
}

@Composable
private fun WorkStatusScreen(
    uiState: WorkStatusScreenState.Loaded,
    workStatusEventHandler: WorkStatusEventHandler
) {
    Column(
        modifier = Modifier
            .background(AppColors.current.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTopBar(headerText = stringResource(id = R.string.work_status_header))

        EmployeeDetailsRow(
            modifier = Modifier
                .padding(start = 16.dp)
                .height(38.dp)
                .align(Alignment.Start),
            firstName = uiState.employee.firstName,
            surname = uiState.employee.surname
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        EmployeeWorkStatusFeed(
            modifier = Modifier.weight(1f),
            workStatuses = uiState.workStatuses
        )
    }
}