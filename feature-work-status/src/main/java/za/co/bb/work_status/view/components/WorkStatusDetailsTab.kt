package za.co.bb.work_status.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import za.co.bb.feature_work_status.R

@Composable
internal fun WorkStatusDetailsTab(
    modifier: Modifier,
    hours: String?,
    wageRate: String?
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(modifier = Modifier.padding(bottom = 8.dp))
        Row {
            Text(text = stringResource(id = R.string.hours_worked) + ": ")
            Text(
                text = hours.toString(),
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
        Row {
            Text(text = stringResource(id = R.string.wage_rate) + ": ")
            Text(
                text = wageRate.toString(),
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
    }
}