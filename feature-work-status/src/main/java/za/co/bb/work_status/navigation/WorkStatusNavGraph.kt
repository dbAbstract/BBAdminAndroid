package za.co.bb.work_status.navigation

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import za.co.bb.core.domain.print
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.components.AppAlertDialog
import za.co.bb.core.util.collectAction
import za.co.bb.feature_work_status.R
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusAction
import za.co.bb.work_status.presentation.home.WorkStatusHomeAction
import za.co.bb.work_status.view.screen.AddWorkStatusScreen
import za.co.bb.work_status.view.screen.WorkStatusHomeScreen
import za.co.bb.work_status.view.util.getAddWorkStatusViewModel
import za.co.bb.work_status.view.util.getWorkStatusViewModel

fun NavGraphBuilder.workStatusNavGraph(
    navigate: (NavAction) -> Unit
) {
    navigation(
        startDestination = Screen.WorkStatusHome.name,
        route = "${Screen.WorkStatusGraph.name}/{$ARG_EMPLOYEE_ID}"
    ) {
        composable(
            route = "${Screen.WorkStatusHome.name}/{$ARG_EMPLOYEE_ID}",
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
            val context = LocalContext.current
            val workStatusViewModel = getWorkStatusViewModel(employeeId = employeeId)
            val uiState by workStatusViewModel.uiState.collectAsStateWithLifecycle()

            WorkStatusHomeScreen(
                uiState = uiState,
                workStatusHomeEventHandler = workStatusViewModel.workStatusHomeEventHandler
            )

            workStatusViewModel.collectAction { action ->
                when (action) {
                    WorkStatusHomeAction.NavigateBack -> navigate(NavAction.NavigateBack)

                    is WorkStatusHomeAction.ShowError -> Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()

                    WorkStatusHomeAction.NavigateToAddWorkStatus -> {
                        navigate(NavAction.NavigateToAddWorkStatus(employeeId))
                    }
                }
            }
        }

        composable(
            route = "${Screen.AddWorkStatus.name}/{$ARG_EMPLOYEE_ID}",
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
            val context = LocalContext.current

            val addWorkStatusHomeViewModel = getAddWorkStatusViewModel(employeeId)
            val uiState by addWorkStatusHomeViewModel.uiState.collectAsStateWithLifecycle()
            var showConfirmationDialog by remember {
                mutableStateOf(false)
            }

            AddWorkStatusScreen(
                uiState = uiState,
                addWorkStatusEventHandler = addWorkStatusHomeViewModel.addWorkStatusEventHandler
            )

            addWorkStatusHomeViewModel.collectAction { addWorkStatusAction ->
                when (addWorkStatusAction) {
                    AddWorkStatusAction.NavigateBack -> navigate(NavAction.NavigateBack)
                    AddWorkStatusAction.ShowConfirmation -> {
                        showConfirmationDialog = true
                    }
                    is AddWorkStatusAction.ShowMessage -> Toast.makeText(context, addWorkStatusAction.message, Toast.LENGTH_SHORT).show()
                }
            }

            if (showConfirmationDialog) {
                AppAlertDialog(
                    title = stringResource(id = R.string.add_work_status_dialog_title),
                    body = "${stringResource(id = R.string.add_work_status_dialog_body)}Hours worked=${uiState.workStatus?.hours}\nWage=${uiState.workStatus?.wageRate?.print()}",
                    onDismissButtonClick = { showConfirmationDialog = false },
                    onConfirmButtonClick = {
                        addWorkStatusHomeViewModel.addWorkStatusEventHandler.addWorkStatus()
                        showConfirmationDialog = false
                    }
                )
            }
        }
    }
}

private const val ARG_EMPLOYEE_ID = "employeeId"
