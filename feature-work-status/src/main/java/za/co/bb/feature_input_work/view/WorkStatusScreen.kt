package za.co.bb.feature_input_work.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.components.AppTopBar
import za.co.bb.core.util.collectAction
import za.co.bb.feature_input_work.presentation.WorkStatusScreenState
import za.co.bb.feature_work_status.R

fun NavGraphBuilder.workStatusScreen() {
    composable(Screen.WorkStatus.name) {
        val inputWorkViewModel = getInputWorkViewModel()
        val uiState by inputWorkViewModel.uiState.collectAsStateWithLifecycle()

        WorkStatusScreen(
            uiState = uiState
        )

        inputWorkViewModel.collectAction {

        }
    }
}

@Composable
private fun WorkStatusScreen(
    uiState: WorkStatusScreenState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        AppTopBar(headerText = stringResource(id = R.string.work_status_header))
    }

}