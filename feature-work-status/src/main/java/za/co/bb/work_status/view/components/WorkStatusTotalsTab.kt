package za.co.bb.work_status.view.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.bb.feature_work_status.R

@Composable
fun WorkStatusTotalsTab(
    modifier: Modifier,
    totalWages: String
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.padding(
                top = 16.dp,
                start =16.dp
            ),
            text = stringResource(id = R.string.total_wages),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    end = 8.dp
                ),
            text = "ZAR $totalWages",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
    }
}