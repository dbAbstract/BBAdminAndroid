package za.co.bb.work_status.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.feature_work_status.R

@Composable
fun WorkingHoursInputComponent(
    modifier: Modifier,
    hoursWorked: String,
    onHoursInputted: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.hours_worked),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(start = 16.dp)
        )

        TextField(
            modifier = Modifier
                .height(50.dp)
                .width(120.dp)
                .padding(start = 12.dp),
            value = hoursWorked,
            onValueChange = onHoursInputted,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = AppColors.current.background,
                focusedIndicatorColor = AppColors.current.primary
            ),
            trailingIcon = {
                Text(text = stringResource(id = R.string.hours))
            }
        )

    }
}