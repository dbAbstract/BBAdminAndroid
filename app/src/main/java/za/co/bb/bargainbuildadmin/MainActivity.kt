package za.co.bb.bargainbuildadmin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import za.co.bb.bargainbuildadmin.presentation.getMainViewModel
import za.co.bb.core.navigation.NavAction
import za.co.bb.core.navigation.Screen
import za.co.bb.core.ui.theme.AppColors

class MainActivity : ComponentActivity() {

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

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = AppColors.current.primary
                    )
                }
            } else {
                BargainBuildAdminApp(
                    navController = navController,
                    navigate = ::navigate,
                    startScreen = if (state.isUserLoggedIn)
                        Screen.HomeScreen
                    else
                        Screen.Login,
                    currentScreen = currentScreen
                )
            }

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

            is NavAction.NavigateToWorkStatus -> {
                navController.navigate("${Screen.WorkStatusHome.name}/${navAction.employeeId}")
            }

            is NavAction.NavigateToAddWorkStatus -> {
                navController.navigate("${Screen.AddWorkStatus.name}/${navAction.employeeId}")
            }

            NavAction.NavigateToHome -> navController.navigate(Screen.HomeScreen.name)
        }
    }
}

private const val TAG = "MainActivity"