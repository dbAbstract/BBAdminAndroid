package za.co.bb.app_admin.navigate

import androidx.navigation.NavGraphBuilder
import za.co.bb.core.navigation.NavAction
import za.co.bb.home.view.homeNavGraph
import za.co.bb.work_status.navigation.workStatusNavGraph

fun NavGraphBuilder.adminAppNavGraph(navigate: (NavAction) -> Unit) {
    homeNavGraph(navigate)
    workStatusNavGraph(navigate)
}