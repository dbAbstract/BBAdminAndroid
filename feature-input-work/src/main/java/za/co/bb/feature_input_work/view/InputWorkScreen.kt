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
import za.co.bb.feature_input_work.R
import za.co.bb.feature_input_work.presentation.InputWorkScreenState

fun NavGraphBuilder.inputWorkScreen() {
    composable(Screen.WorkStatus.name) {
        val inputWorkViewModel = getInputWorkViewModel()
        val uiState by inputWorkViewModel.uiState.collectAsStateWithLifecycle()

        InputWorkScreen(
            uiState = uiState
        )

        inputWorkViewModel.collectAction {

        }
    }
}

@Composable
private fun InputWorkScreen(
    uiState: InputWorkScreenState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        AppTopBar(headerText = stringResource(id = R.string.input_work_header))
    }

}