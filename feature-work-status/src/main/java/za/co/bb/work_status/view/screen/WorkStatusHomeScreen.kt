package za.co.bb.work_status.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import za.co.bb.core.domain.print
import za.co.bb.core.ui.components.AppAlertDialog
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.feature_work_status.R
import za.co.bb.work_status.presentation.home.WorkStatusHomeEventHandler
import za.co.bb.work_status.presentation.home.WorkStatusHomeScreenState
import za.co.bb.work_status.view.components.EmployeeDetailsRow
import za.co.bb.work_status.view.components.EmployeeWorkStatusFeed
import za.co.bb.work_status.view.components.WorkStatusHomeScreenError
import za.co.bb.work_status.view.components.WorkStatusHomeScreenLoading
import za.co.bb.work_status.view.components.WorkStatusScreenTopBar
import za.co.bb.work_status.view.components.WorkStatusTotalsTab

@Composable
internal fun WorkStatusHomeScreen(
    uiState: WorkStatusHomeScreenState,
    workStatusHomeEventHandler: WorkStatusHomeEventHandler
) {
    when (uiState) {
        WorkStatusHomeScreenState.Error -> {
            WorkStatusHomeScreenError(onBack = workStatusHomeEventHandler::onBack)
        }

        WorkStatusHomeScreenState.Loading -> {
            WorkStatusHomeScreenLoading(onBack = workStatusHomeEventHandler::onBack)
        }

        is WorkStatusHomeScreenState.Loaded -> {
            WorkStatusHomeScreen(
                uiState = uiState,
                workStatusHomeEventHandler = workStatusHomeEventHandler
            )
        }
    }
}

@Composable
private fun WorkStatusHomeScreen(
    uiState: WorkStatusHomeScreenState.Loaded,
    workStatusHomeEventHandler: WorkStatusHomeEventHandler
) {
    val showDeleteIcon by remember(uiState.selectedWorkStatuses) {
        derivedStateOf { uiState.selectedWorkStatuses.isNotEmpty() }
    }
    var showDeleteWorkStatusPopup by remember {
        mutableStateOf(false)
    }

    Scaffold(
        backgroundColor = AppColors.current.background,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = TOTALS_TAB_HEIGHT.dp),
                onClick = workStatusHomeEventHandler::navigateToAddWorkStatus,
                backgroundColor = AppColors.current.secondary
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WorkStatusScreenTopBar(
                onBack = workStatusHomeEventHandler::onBack,
                showDeleteIcon = showDeleteIcon,
                onDeleteClick = { showDeleteWorkStatusPopup = true }
            )

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
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 8.dp)
                    .padding(bottom = TOTALS_TAB_HEIGHT.dp)
                    .weight(1f),
                workStatuses = uiState.workStatuses,
                selectedWorkStatuses = uiState.selectedWorkStatuses,
                onWorkStatusSelected = workStatusHomeEventHandler::onWorkStatusSelected,
                onWorkStatusDeselected = workStatusHomeEventHandler::onWorkStatusDeselected
            )

            WorkStatusTotalsTab(
                modifier = Modifier
                    .height(TOTALS_TAB_HEIGHT.dp)
                    .fillMaxWidth()
                    .background(AppColors.current.primary),
                totalWages = "ZAR ${uiState.totalWage.print()}"
            )
        }
    }


    if (showDeleteWorkStatusPopup) {
        AppAlertDialog(
            title = stringResource(id = R.string.delete_work_status_dialog_title),
            body = stringResource(id = R.string.delete_work_status_dialog_body),
            onDismissButtonClick = {
                showDeleteWorkStatusPopup = false
            },
            onConfirmButtonClick = {
                showDeleteWorkStatusPopup = false
                workStatusHomeEventHandler.deleteSelectedWorkStatuses()
            }
        )
    }
}

private const val TOTALS_TAB_HEIGHT = 80