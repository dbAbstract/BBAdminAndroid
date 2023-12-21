package za.co.bb.work_status.view.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import za.co.bb.core.ui.components.AppTopBar
import za.co.bb.feature_work_status.R
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusEventHandler
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusScreenState
import za.co.bb.work_status.view.components.WageSelectionComponent
import za.co.bb.work_status.view.components.WorkStatusDetailsTab
import za.co.bb.work_status.view.components.WorkingHoursInputComponent

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AddWorkStatusScreen(
    uiState: AddWorkStatusScreenState,
    addWorkStatusEventHandler: AddWorkStatusEventHandler
) {
    val wagesPagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0.0f,
        pageCount = uiState.wages::size
    )

    LaunchedEffect(key1 = wagesPagerState) {
        snapshotFlow { wagesPagerState.settledPage }.collectLatest(addWorkStatusEventHandler::onWageSelected)
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppTopBar(
                headerText = uiState.employee?.let { employee ->
                    employee.firstName + " " + employee.surname
                } ?: "",
                onBack = addWorkStatusEventHandler::navigateBack,
            )

            WageSelectionComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .height(140.dp),
                wages = uiState.wages,
                horizontalPagerState = wagesPagerState
            )

            WorkingHoursInputComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                hoursWorked = uiState.workStatus?.hours ?: "0",
                onHoursInputted = addWorkStatusEventHandler::onWorkingHoursInput
            )

            Spacer(modifier = Modifier.height(40.dp))

            AppButton(
                modifier = Modifier
                    .height(60.dp)
                    .width(300.dp),
                shape = CircleShape,
                text = stringResource(id = R.string.add_work_hours),
                onClick = addWorkStatusEventHandler::onAddWorkStatusClick
            )

            Spacer(modifier = Modifier.weight(1f))

            WorkStatusDetailsTab(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 20.dp),
                hours = uiState.workStatus?.hours.toString(),
                wageRate = uiState.workStatus?.wageRate.toString()
            )
        }
    }
}