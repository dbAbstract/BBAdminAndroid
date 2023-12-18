package za.co.bb.bargainbuildadmin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.components.AppBottomBar
import za.co.bb.core.ui.components.BOTTOM_BAR_HEIGHT
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.home.view.homeScreen
import za.co.bb.work_status.navigation.featureWorkStatus

@Composable
internal fun BargainBuildAdminApp(
    navController: NavHostController,
    navigate: (NavAction) -> Unit,
    currentScreen: Screen
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AppBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(BOTTOM_BAR_HEIGHT.dp)
                .background(AppColors.current.primary),
            onNavIconClick = navigate,
            currentScreen = currentScreen
        )

        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = Screen.HomeScreen.name
        ) {
            homeScreen(navigate = navigate)
            featureWorkStatus(navigate = navigate)
        }
    }
}