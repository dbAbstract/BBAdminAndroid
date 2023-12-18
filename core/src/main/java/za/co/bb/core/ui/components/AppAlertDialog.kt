package za.co.bb.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.bb.core.R
import za.co.bb.core.ui.theme.AppColors

@Composable
fun AppAlertDialog(
    title: String,
    body: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    shape: Shape = RoundedCornerShape(size = 24.dp),
    cancelButtonText: String = stringResource(id = R.string.cancel),
    confirmButtonText: String = stringResource(id = R.string.confirm)
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        },
        text = {
            Text(text = body)
        },
        shape = shape,
        backgroundColor = AppColors.current.surface,
        buttons = {
            Divider(modifier = Modifier.offset(y = 16.dp))
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .height(44.dp)
                    .offset(y = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(onClick = onDismiss)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = cancelButtonText
                    )
                }
                Divider(modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight())
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(onClick = onConfirm)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = confirmButtonText,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun AppAlertDialogPreview() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        AppAlertDialog(
            title = "Some title",
            body = "Fee fi fo fum, I smell the blood of an Englishman.",
            onDismiss = {},
            onConfirm = {}
        )
    }

}