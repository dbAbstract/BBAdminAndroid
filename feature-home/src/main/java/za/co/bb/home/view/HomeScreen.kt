package za.co.bb.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.components.AppTopBar
import za.co.bb.core.ui.components.BOTTOM_BAR_HEIGHT
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.core.util.collectAction
import za.co.bb.home.R
import za.co.bb.home.presentation.HomeScreenAction
import za.co.bb.home.presentation.HomeScreenEventHandler
import za.co.bb.home.presentation.HomeScreenState
import za.co.bb.home.view.ui.EmployeeWageStatusList

fun NavGraphBuilder.homeScreen(
    navigate: (Screen) -> Unit
) {
    composable(route = Screen.HomeScreen.name) {
        val homeScreenViewModel = getHomeScreenViewModel()
        val uiState by homeScreenViewModel.uiState.collectAsStateWithLifecycle()

        HomeScreen(
            uiState = uiState,
            homeScreenEventHandler = homeScreenViewModel.homeScreenEventHandler
        )

        homeScreenViewModel.collectAction { action ->
            when (action) {
                HomeScreenAction.NavigateToAddEmployee -> navigate(Screen.AddEmployee)
                HomeScreenAction.NavigateToWorkStatus -> navigate(Screen.WorkStatus)
            }
        }
    }
}

@Composable
private fun HomeScreen(
    uiState: HomeScreenState,
    homeScreenEventHandler: HomeScreenEventHandler
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.padding(bottom = BOTTOM_BAR_HEIGHT.dp),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = AppColors.current.secondary,
                onClick = homeScreenEventHandler::onAddEmployeeClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = AppColors.current.onSecondary
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            AppTopBar(headerText = stringResource(id = R.string.employees_header))

            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .navigationBarsPadding()
            ) {

                when (uiState.isLoading) {
                    true -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = AppColors.current.primary
                        )
                    }

                    false -> {
                        EmployeeWageStatusList(
                            modifier = Modifier.fillMaxSize(),
                            wageStatusList = uiState.employeeWageStatuses,
                            onWageStatusClick = homeScreenEventHandler::navigateToWorkStatus
                        )
                    }
                }
            }
        }
    }
}