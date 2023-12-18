package za.co.bb.bargainbuildadmin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private var currentScreen by mutableStateOf(Screen.HomeScreen)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            navController = rememberNavController()
            val backStack by navController.currentBackStackEntryAsState()

            BargainBuildAdminApp(
               navController = navController,
               navigate = ::navigate,
               currentScreen = currentScreen
            )

            LaunchedEffect(key1 = backStack) {
                backStack?.destination?.route?.let { route ->
                    val originalRoute = route.substringBefore("/{")
                    try {
                        currentScreen = Screen.valueOf(originalRoute)
                    } catch (t: Throwable) {
                        Log.e(TAG, "Error parsing current route with exception=$t")
                    }
                }
            }
        }
    }

    private fun navigate(navAction: NavAction) {
        when (navAction) {
            NavAction.NavigateBack -> navController.popBackStack()

            NavAction.NavigateToAddEmployee -> {
                navController.navigate(Screen.AddEmployee.name)
            }

            is NavAction.NavigateToWorkStatus -> {
                navController.navigate("${Screen.WorkStatusHome.name}/${navAction.employeeId}")
            }
        }
    }
}

private const val TAG = "MainActivity"