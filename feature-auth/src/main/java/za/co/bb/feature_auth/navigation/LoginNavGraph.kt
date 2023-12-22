package za.co.bb.feature_auth.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import za.co.bb.core.navigation.Screen

fun NavGraphBuilder.loginNavGraph() {
    composable(route = Screen.Login.name) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "Login screen AHHHHH!", modifier = Modifier.align(Alignment.Center))
        }
    }
}