package za.co.bb.feature_input_work.view.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.feature_input_work.domain.model.WorkStatus
import za.co.bb.feature_work_status.R

@Composable
internal fun EmployeeWorkStatusFeed(
    modifier: Modifier,
    workStatuses: List<WorkStatus>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(workStatuses) { workStatus ->
            WorkStatusCard(
                modifier = Modifier.fillMaxWidth(),
                workStatus = workStatus
            )
        }
    }
}

@Composable
private fun WorkStatusCard(
    modifier: Modifier,
    workStatus: WorkStatus
) {
    Card(
        modifier = modifier.height(130.dp),
        shape = RoundedCornerShape(24.dp),
        backgroundColor = AppColors.current.surface
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .padding(start = CONTENT_PADDING.dp)
                    .height(40.dp),
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
                    text = " ${workStatus.wageRate}",
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
                    text = " ${workStatus.createdAt}",
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
//internal data class WorkStatus(
//    val hours: Long,
//    val wageRate: Rand,
//    val wageId: String,
//    val createdAt: LocalDateTime,
//    val amountDue: Rand
//)