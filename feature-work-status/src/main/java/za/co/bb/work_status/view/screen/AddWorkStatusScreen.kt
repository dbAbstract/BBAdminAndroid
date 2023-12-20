package za.co.bb.work_status.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import za.co.bb.core.ui.components.AppTopBar
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusEventHandler
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusScreenState
import za.co.bb.work_status.view.components.WageCarousel

@Composable
internal fun AddWorkStatusScreen(
    uiState: AddWorkStatusScreenState,
    addWorkStatusEventHandler: AddWorkStatusEventHandler
) {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTopBar(
                headerText = uiState.employee?.let { employee ->
                    employee.firstName + " " + employee.surname
                } ?: "",
                onBack = addWorkStatusEventHandler::navigateBack,
            )

            Spacer(modifier = Modifier.height(16.dp))

            WageCarousel(
                modifier = Modifier
                    .height(120.dp)
                    .padding(horizontal = 8.dp),
                wages = uiState.wages
            )
        }
    }
}