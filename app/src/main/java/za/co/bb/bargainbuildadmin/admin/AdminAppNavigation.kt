package za.co.bb.bargainbuildadmin.admin

import androidx.navigation.NavHostController
import za.co.bb.core.navigation.NavAction

fun NavHostController.navigate(navAction: NavAction): Unit = when (navAction) {
    NavAction.NavigateBack -> TODO()

    is NavAction.NavigateToAddWorkStatus -> TODO()

    NavAction.NavigateToAdmin -> TODO()

    NavAction.NavigateToHome -> TODO()

    is NavAction.NavigateToWorkStatus -> TODO()
}