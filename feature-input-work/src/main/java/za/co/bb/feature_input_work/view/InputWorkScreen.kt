package za.co.bb.feature_input_work.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import za.co.bb.core.navigation.Screen
import za.co.bb.feature_input_work.presentation.InputWorkScreenState

fun NavGraphBuilder.inputWorkScreen() {
    composable(Screen.InputWorkHours.name) {

    }
}

@Composable
private fun InputWorkScreen(
    uiState: InputWorkScreenState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }

}