package za.co.bb.bargainbuildadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import za.co.bb.bargainbuildadmin.presentation.getMainViewModel
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen

class AuthActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private var currentScreen by mutableStateOf(Screen.HomeScreen)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            navController = rememberNavController()

            val backStack by navController.currentBackStackEntryAsState()
            val mainViewModel = getMainViewModel()
            val state by mainViewModel.state.collectAsStateWithLifecycle()
            val userType = remember(state) {
                state.userType
            }

//            LaunchedEffect(key1 = backStack) {
//                backStack?.destination?.route?.let { route ->
//                    val originalRoute = route.substringBefore("/{")
//                    try {
//                        currentScreen = Screen.valueOf(originalRoute)
//                    } catch (t: Throwable) {
//                        Log.e(TAG, "Error parsing current route with exception=$t")
//                    }
//                }
//            }
        }
    }

    private fun navigate(navAction: NavAction) {
        when (navAction) {
            NavAction.NavigateBack -> navController.popBackStack()

            is NavAction.NavigateToWorkStatus -> {
                navController.navigate("${Screen.WorkStatusHome.name}/${navAction.employeeId}")
            }

            is NavAction.NavigateToAddWorkStatus -> {
                navController.navigate("${Screen.AddWorkStatus.name}/${navAction.employeeId}")
            }

            NavAction.NavigateToHome -> if (currentScreen != Screen.HomeScreen) {
                navController.navigate(Screen.HomeScreen.name) {
                    popUpTo(Screen.Login.name) {
                        inclusive = true
                    }
                }
            }

            NavAction.NavigateToAdmin -> if (currentScreen != Screen.Admin) {
                navController.navigate(Screen.Admin.name)
            }
        }
    }
}

private const val TAG = "MainActivity"