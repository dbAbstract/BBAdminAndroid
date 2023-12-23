package za.co.bb.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import za.co.bb.core.ui.components.AppTopBar
import za.co.bb.core.ui.components.BOTTOM_BAR_HEIGHT
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.home.R
import za.co.bb.home.presentation.HomeScreenEventHandler
import za.co.bb.home.presentation.HomeScreenState
import za.co.bb.home.view.ui.EmployeeWageStatusList

@Composable
internal fun HomeScreen(
    uiState: HomeScreenState,
    homeScreenEventHandler: HomeScreenEventHandler
) {
    val scaffoldState = rememberScaffoldState()
    var displayed by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        if (displayed) {
            homeScreenEventHandler.refresh()
        } else {
            displayed = true
        }
    }

    Scaffold(
        modifier = Modifier.padding(bottom = BOTTOM_BAR_HEIGHT.dp),
        scaffoldState = scaffoldState
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