package za.co.bb.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.theme.AppColors

@Composable
internal fun BottomBarNavIcon(
    modifier: Modifier = Modifier,
    destination: Screen,
    currentDestination: Screen,
    iconVector: ImageVector,
    onClick: (NavAction) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                when (destination) {
                    Screen.HomeScreen -> onClick(NavAction.NavigateToHome)
                    Screen.Admin -> onClick(NavAction.NavigateToAdmin)
                    else -> {}
                }
            }
        ) {
            Icon(
                imageVector = iconVector,
                contentDescription = null,
                tint = if (currentDestination == destination)
                    AppColors.current.secondary
                else
                    AppColors.current.onPrimary
            )
        }
        if (currentDestination == destination) Divider(
            modifier = Modifier.fillMaxWidth(0.8f),
            color = AppColors.current.secondary,
            thickness = 4.dp
        )
    }
}