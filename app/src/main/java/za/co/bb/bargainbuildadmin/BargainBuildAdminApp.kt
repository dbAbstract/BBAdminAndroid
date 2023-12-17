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
import androidx.navigation.compose.rememberNavController
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.components.AppBottomBar
import za.co.bb.core.ui.components.BOTTOM_BAR_HEIGHT
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.feature_input_work.view.workStatusScreen
import za.co.bb.home.view.homeScreen

@Composable
internal fun BargainBuildAdminApp(
    navigate: (NavHostController, Screen) -> Unit,
    currentScreen: Screen
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.name
        ) {
            homeScreen(
                navigate = { screen ->
                    navigate(navController, screen)
                }
            )
            workStatusScreen()
        }

        AppBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(BOTTOM_BAR_HEIGHT.dp)
                .background(AppColors.current.primary),
            onNavIconClick = { screen ->
                navigate(navController, screen)
            },
            currentScreen = currentScreen
        )
    }
}