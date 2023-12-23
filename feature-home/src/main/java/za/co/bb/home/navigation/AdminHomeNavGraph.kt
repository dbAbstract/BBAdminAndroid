package za.co.bb.home.navigation

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen
import za.co.bb.core.util.collectAction
import za.co.bb.home.presentation.HomeScreenAction
import za.co.bb.home.view.HomeScreen
import za.co.bb.home.view.getHomeScreenViewModel

fun NavGraphBuilder.adminHomeNavGraph(
    navigate: (NavAction) -> Unit
) {
    composable(route = Screen.HomeScreen.name) {
        val context = LocalContext.current
        val homeScreenViewModel = getHomeScreenViewModel()
        val uiState by homeScreenViewModel.uiState.collectAsStateWithLifecycle()

        HomeScreen(
            uiState = uiState,
            homeScreenEventHandler = homeScreenViewModel.homeScreenEventHandler
        )

        homeScreenViewModel.collectAction { action ->
            when (action) {
                is HomeScreenAction.NavigateToWorkStatus -> navigate(
                    NavAction.NavigateToWorkStatus(action.employeeId)
                )

                is HomeScreenAction.ShowError -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}