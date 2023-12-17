package za.co.bb.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.bb.core.ui.theme.AppColors

@Composable
fun AppTopBar(
    headerText: String,
    onBack: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .height(TOP_BAR_HEIGHT.dp)
            .fillMaxWidth()
            .background(AppColors.current.primary)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            onBack?.let {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = headerText,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = AppColors.current.onPrimary
                )
            )
        }
    }
}

@Composable
fun AppTopBar(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .height(TOP_BAR_HEIGHT.dp)
            .fillMaxWidth()
            .background(AppColors.current.primary)
    ) {
        content()
    }
}