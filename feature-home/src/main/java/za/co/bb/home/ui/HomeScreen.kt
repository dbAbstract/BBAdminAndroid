package za.co.bb.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.home.R
import za.co.bb.home.di.DependencyContainer
import za.co.bb.home.presentation.HomeScreenState
import za.co.bb.home.presentation.HomeScreenViewModel
import za.co.bb.home.presentation.HomeScreenViewModelFactory
import za.co.bb.home.ui.components.EmployeeWageStatusList

fun NavGraphBuilder.homeScreen() {
    composable(route = Screen.HomeScreen.name) {
        val employeeListScreenViewModel = viewModel<HomeScreenViewModel>(
            factory = HomeScreenViewModelFactory(
                getWageStatusForEmployees = DependencyContainer.getWageStatusForEmployees
            )
        )
        val uiState by employeeListScreenViewModel.uiState.collectAsStateWithLifecycle()

        HomeScreen(
            uiState = uiState
        )
    }
}

@Composable
private fun HomeScreen(
    uiState: HomeScreenState
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
                text = stringResource(id = R.string.employees_header),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = AppColors.current.onPrimary
                ),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart)
                    .statusBarsPadding()
            )
        }

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
                        wageStatusList = uiState.employeeWageStatuses
                    )
                }
            }
        }
    }
}