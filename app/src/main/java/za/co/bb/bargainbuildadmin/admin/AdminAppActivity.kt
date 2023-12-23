package za.co.bb.bargainbuildadmin.admin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.components.AppBottomBar
import za.co.bb.core.ui.components.BOTTOM_BAR_HEIGHT
import za.co.bb.core.ui.components.BottomNavBarItem
import za.co.bb.core.ui.theme.AppColors

class AdminAppActivity : ComponentActivity() {
    private val tag = this::class.java.simpleName
    private var currentScreen by mutableStateOf(Screen.HomeScreen)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val backStack by navController.currentBackStackEntryAsState()

            LaunchedEffect(key1 = backStack) {
                val routeString =
                    backStack?.destination?.route?.substringBefore("/") ?: return@LaunchedEffect

                try {
                    currentScreen = Screen.valueOf(routeString)
                } catch (t: Throwable) {
                    Log.e(tag, "Failed to parse route=$routeString")
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                AppBottomBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(BOTTOM_BAR_HEIGHT.dp)
                        .background(AppColors.current.primary),
                    onNavIconClick = navController::navigate,
                    currentScreen = currentScreen,
                    bottomNavBarItems = listOf(
                        BottomNavBarItem(
                            screen = Screen.HomeScreen, iconVector = Icons.Filled.Home
                        ),
                        BottomNavBarItem(
                            screen = Screen.Admin, iconVector = Icons.Filled.Settings
                        )
                    )
                )

                AdminApp(navigate = navController::navigate)
            }
        }
    }
}