package za.co.bb.work_status.view.screen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import za.co.bb.core.ui.theme.AppColors

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(8.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = AppColors.current.primary,
        contentColor = AppColors.current.onPrimary
    ),
    enabled: Boolean = true,
    text: String? = null,
    content: (@Composable () -> Unit)? = null
) {
    require(
        value = (text != null) || (content != null),
        lazyMessage = {
            "This Composable requires either the 'text' argument or 'content' argument to be non-null."
        }
    )

    Button(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        enabled = enabled,
        colors = colors
    ) {
        content?.let {
            it()
        } ?: Text(text = text ?: "null")
    }
}