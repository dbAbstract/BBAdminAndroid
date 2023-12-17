package za.co.bb.bargainbuildadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import za.co.bb.core.navigation.Screen

class MainActivity : ComponentActivity() {

    private var currentScreen by mutableStateOf(Screen.HomeScreen)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           BargainBuildAdminApp(
               navigate = ::navigate,
               currentScreen = currentScreen
           )
        }
    }

    private fun navigate(
        navHostController: NavHostController,
        screen: Screen
    ) {
        if (navHostController.currentDestination?.route?.equals(screen.name) == true) {
            return
        }

        currentScreen = screen
        navHostController.navigate(screen.name) {
            popUpTo(navHostController.graph.startDestinationId)
        }
    }
}