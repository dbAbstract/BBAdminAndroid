package za.co.bb.bargainbuildadmin.admin

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import za.co.bb.app_admin.navigate.adminAppNavGraph
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen

@Composable
fun AdminApp(navigate: (NavAction) -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.name
    ) {
        adminAppNavGraph(navigate = navigate)
    }
}