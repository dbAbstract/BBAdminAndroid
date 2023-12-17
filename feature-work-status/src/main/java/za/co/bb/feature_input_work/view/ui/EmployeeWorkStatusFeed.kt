package za.co.bb.feature_input_work.view.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import za.co.bb.feature_input_work.domain.model.WorkStatus

@Composable
internal fun EmployeeWorkStatusFeed(
    modifier: Modifier,
    workStatuses: List<WorkStatus>
) {
    LazyColumn(modifier = modifier) {
        items(workStatuses) { workStatus ->

        }
    }
}