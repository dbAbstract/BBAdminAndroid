package za.co.bb.bargainbuildadmin.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val authViewModel = authViewModel()
            val authState by authViewModel.state.collectAsStateWithLifecycle()

            when (authState.isUserLoggedIn) {
                true -> TODO()
                false -> {

                }
            }

        }
    }
}

private const val TAG = "AuthActivity"