package za.co.bb.bargainbuildadmin.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import za.co.bb.core.navigation.NavAction

@Composable
fun AdminApp(navigate: (NavAction) -> Unit) {
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {

    }
}