package za.co.bb.feature_input_work.view.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.bb.core.ui.components.AppTopBar
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.feature_work_status.R

@Composable
internal fun WorkStatusScreenError(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(AppColors.current.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        AppTopBar(
            headerText = stringResource(id = R.string.work_status_header)
        )
        AlertDialog(
            title = {
                Text(
                    text = stringResource(id = R.string.error),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.error_message),
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
            },
            onDismissRequest = onBack,
            buttons = {
                Button(
                    modifier = Modifier
                        .offset(y = (6).dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppColors.current.primary,
                        contentColor = AppColors.current.onPrimary
                    ),
                    onClick = onBack
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            backgroundColor = AppColors.current.surface,
            contentColor = AppColors.current.onPrimary
        )
    }
}

@Preview
@Composable
private fun Preview() {
    WorkStatusScreenError(onBack = {})
}