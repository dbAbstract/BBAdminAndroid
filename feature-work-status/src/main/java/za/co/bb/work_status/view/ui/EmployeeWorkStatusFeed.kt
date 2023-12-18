package za.co.bb.work_status.view.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.bb.core.domain.print
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.feature_work_status.R
import za.co.bb.work_status.domain.model.WorkStatus

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun EmployeeWorkStatusFeed(
    modifier: Modifier,
    workStatuses: List<WorkStatus>,
    selectedWorkStatuses: Set<WorkStatus>,
    onWorkStatusSelected: (WorkStatus) -> Unit,
    onWorkStatusDeselected: (WorkStatus) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(workStatuses) { workStatus ->
            val isSelected by remember(selectedWorkStatuses) {
                derivedStateOf { selectedWorkStatuses.any { it.workHoursId == workStatus.workHoursId } }
            }

            WorkStatusCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            if (isSelected) onWorkStatusDeselected(workStatus)
                        },
                        onLongClick = {
                            onWorkStatusSelected(workStatus)
                        }
                    ),
                workStatus = workStatus,
                isSelected = isSelected
            )
        }
    }
}

@Composable
private fun WorkStatusCard(
    modifier: Modifier,
    workStatus: WorkStatus,
    isSelected: Boolean
) {
    Card(
        modifier = modifier.height(130.dp),
        shape = RoundedCornerShape(24.dp),
        backgroundColor = AppColors.current.surface,
        border = if (isSelected) BorderStroke(
            width = 2.dp,
            color = AppColors.current.secondary
        ) else null
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .padding(start = CONTENT_PADDING.dp)
                    .height(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.amount_due),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(end = END_PADDING.dp),
                    text = " ZAR ${String.format("%.2f", workStatus.amountDue)}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Divider()

            Row(
                modifier = Modifier.padding(start = CONTENT_PADDING.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.wage_rate),
                    style = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(end = CONTENT_PADDING.dp),
                    text = " ${workStatus.wageRate.print()}",
                    style = TextStyle(fontWeight = FontWeight.SemiBold)
                )
            }

            Row(
                modifier = Modifier.padding(start = CONTENT_PADDING.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.created_at),
                    style = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(end = CONTENT_PADDING.dp),
                    text = " ${workStatus.createdAt.dayOfMonth}-${workStatus.createdAt.month.name}-${workStatus.createdAt.year} ${workStatus.createdAt.time}",
                    style = TextStyle(fontWeight = FontWeight.SemiBold)
                )
            }

            Row(
                modifier = Modifier.padding(start = CONTENT_PADDING.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.hours_worked),
                    style = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(end = CONTENT_PADDING.dp),
                    text = " ${workStatus.hours} hours",
                    style = TextStyle(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}

private const val END_PADDING = 8
private const val CONTENT_PADDING = 16