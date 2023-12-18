package za.co.bb.work_status.navigation

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen
import za.co.bb.core.util.collectAction
import za.co.bb.work_status.presentation.WorkStatusAction
import za.co.bb.work_status.view.WorkStatusScreen
import za.co.bb.work_status.view.getWorkStatusViewModel

fun NavGraphBuilder.featureWorkStatus(
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

            WorkStatusScreen(
                uiState = uiState,
                workStatusEventHandler = workStatusViewModel.workStatusEventHandler
            )

            workStatusViewModel.collectAction { action ->
                when (action) {
                    WorkStatusAction.NavigateBack -> navigate(NavAction.NavigateBack)

                    is WorkStatusAction.ShowError -> Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}

private const val ARG_EMPLOYEE_ID = "employeeId"
