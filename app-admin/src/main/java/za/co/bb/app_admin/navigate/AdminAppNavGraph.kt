package za.co.bb.app_admin.navigate

import androidx.navigation.NavGraphBuilder
import za.co.bb.core.navigation.NavAction
import za.co.bb.home.navigation.adminHomeNavGraph
import za.co.bb.work_status.navigation.workStatusNavGraph

fun NavGraphBuilder.adminAppNavGraph(navigate: (NavAction) -> Unit) {
    adminHomeNavGraph(navigate)
    workStatusNavGraph(navigate)
}