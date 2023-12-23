package za.co.bb.bargainbuildadmin.admin

import androidx.navigation.NavHostController
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen

fun NavHostController.navigate(navAction: NavAction) {
    when (navAction) {
        NavAction.NavigateBack -> popBackStack()

        is NavAction.NavigateToAddWorkStatus -> {
            navigate("${Screen.WorkStatusHome.name}/${navAction.employeeId}")
        }

        NavAction.NavigateToAdmin -> navigate(Screen.Admin)

        NavAction.NavigateToHome -> navigate(Screen.HomeScreen)

        is NavAction.NavigateToWorkStatus -> navigate("${Screen.AddWorkStatus.name}/${navAction.employeeId}")
    }
}

fun NavHostController.navigate(route: Screen) {
    if (currentScreen != route) navigate(route.name)
}

val NavHostController.currentScreen: Screen
    get() {
        val currentRoute = currentBackStackEntry?.destination?.route?.substringBefore("/") ?: return Screen.HomeScreen

        return try {
            Screen.valueOf(currentRoute)
        } catch (t: Throwable) {
            Screen.HomeScreen
        }
    }