package za.co.bb.work_status.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusEventHandler
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddWorkStatusBottomSheet(
    addWorkStatusScreenState: AddWorkStatusScreenState,
    addWorkStatusEventHandler: AddWorkStatusEventHandler
) {
    ModalBottomSheet(
        onDismissRequest = addWorkStatusEventHandler::navigateBack,
    ) {
        Box(modifier = Modifier.fillMaxSize())
    }
}