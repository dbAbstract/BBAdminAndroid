package za.co.bb.core.ui.theme

import android.annotation.SuppressLint
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class BargainBuildTheme(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color
) {
    companion object {
        val Light = BargainBuildTheme(
            primary = Color(0xFFFCA719),
            onPrimary = Color.Black,
            secondary = Color(0xFF8A0500),
            onSecondary = Color.White
        )
    }
}

@SuppressLint("CompositionLocalNaming")
val AppColors = compositionLocalOf { BargainBuildTheme.Light }
