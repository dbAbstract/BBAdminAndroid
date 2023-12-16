package za.co.bb.bargainbuildadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.home.ui.homeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           BargainBuildAdminApp(navigate = ::navigate)
        }
    }

    private fun navigate(
        navHostController: NavHostController,
        screen: Screen
    ) {
        navHostController.navigate(screen.name) {
            popUpTo(navHostController.graph.startDestinationId)
        }
    }
}

@Composable
private fun BargainBuildAdminApp(
    navigate: (NavHostController, Screen) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.name
        ) {
            homeScreen()
        }

        AppBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(80.dp)
                .background(AppColors.current.primary),
            onNavIconClick = { screen ->
                navigate(navController, screen)
            }
        )
    }
}