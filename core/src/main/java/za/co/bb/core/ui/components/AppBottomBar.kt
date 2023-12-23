package za.co.bb.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen

@Composable
fun AppBottomBar(
    modifier: Modifier,
    onNavIconClick: (NavAction) -> Unit,
    currentScreen: Screen,
    bottomNavBarItems: List<BottomNavBarItem>
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            bottomNavBarItems.forEach {
                BottomBarNavIcon(
                    modifier = Modifier.width(40.dp),
                    destination = it.screen,
                    currentDestination = currentScreen,
                    iconVector = it.iconVector,
                    onClick = onNavIconClick
                )
            }
        }
    }
}

data class BottomNavBarItem(
    val screen: Screen,
    val iconVector: ImageVector
)