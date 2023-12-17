package za.co.bb.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.bb.core.ui.theme.AppColors

@Composable
fun AppTopBar(headerText: String) {
    Box(
        modifier = Modifier
            .height(TOP_BAR_HEIGHT.dp)
            .fillMaxWidth()
            .background(AppColors.current.primary)
    ) {
        Text(
            text = headerText,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = AppColors.current.onPrimary
            ),
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterStart)
                .statusBarsPadding()
        )
    }
}