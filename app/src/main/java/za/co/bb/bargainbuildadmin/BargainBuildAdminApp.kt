package za.co.bb.bargainbuildadmin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.components.AppBottomBar
import za.co.bb.core.ui.components.BOTTOM_BAR_HEIGHT
import za.co.bb.core.ui.components.BottomNavBarItem
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.feature_auth.navigation.loginNavGraph
import za.co.bb.home.view.homeNavGraph
import za.co.bb.user.domain.model.UserType
import za.co.bb.work_status.navigation.workStatusNavGraph

@Composable
internal fun BargainBuildAdminApp(
    navController: NavHostController,
    navigate: (NavAction) -> Unit,
    startScreen: Screen,
    currentScreen: Screen,
    userType: UserType
) {
    val bottomNavBarItems = remember(userType) {
        when (userType) {
            UserType.Employee -> employeeBottomNavBarList
            UserType.Admin -> adminBottomNavBarList
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AppBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(BOTTOM_BAR_HEIGHT.dp)
                .background(AppColors.current.primary),
            onNavIconClick = navigate,
            currentScreen = currentScreen,
            bottomNavBarItems = bottomNavBarItems
        )

        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = startScreen.name
        ) {
            homeNavGraph(navigate = navigate)
            workStatusNavGraph(navigate = navigate)
            loginNavGraph(navigate = navigate)
        }
    }
}

private val adminBottomNavBarList = listOf(
    BottomNavBarItem(
        screen = Screen.HomeScreen,
        iconVector = Icons.Filled.Home
    ),
    BottomNavBarItem(
        screen = Screen.Admin,
        iconVector = Icons.Filled.Settings
    )
)

private val employeeBottomNavBarList = listOf(
    BottomNavBarItem(
        screen = Screen.HomeScreen,
        iconVector = Icons.Filled.Home
    )
)