package za.co.bb.feature_input_work.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.components.AppTopBar
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.core.util.collectAction
import za.co.bb.feature_input_work.presentation.WorkStatusEventHandler
import za.co.bb.feature_input_work.presentation.WorkStatusScreenState
import za.co.bb.feature_input_work.view.ui.WorkStatusScreenError
import za.co.bb.feature_input_work.view.ui.WorkStatusScreenLoading
import za.co.bb.feature_work_status.R

fun NavGraphBuilder.workStatusScreen() {
    composable(Screen.WorkStatus.name) {
        val workStatusViewModel = getWorkStatusViewModel()
        val uiState by workStatusViewModel.uiState.collectAsStateWithLifecycle()

        WorkStatusScreen(
            uiState = uiState,
            workStatusEventHandler = workStatusViewModel.workStatusEventHandler
        )

        workStatusViewModel.collectAction {

        }
    }
}

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
        verticalArrangement = Arrangement.Top
    ) {
        AppTopBar {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.current.primary)
            ) {
                Text(
                    text = stringResource(id = R.string.work_status_header),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = AppColors.current.onPrimary
                    )
                )
                Text(
                    text = uiState.employee.surname,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(", ")
                Text(text = uiState.employee.firstName)
            }

        }
        LazyColumn(modifier = Modifier.weight(1f)) {

        }
    }
}