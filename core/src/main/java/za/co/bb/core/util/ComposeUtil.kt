package za.co.bb.core.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import za.co.bb.core.presentation.BaseViewModel

@SuppressLint("ComposableNaming")
@Composable
fun <Action> BaseViewModel<Action>.collectAction(block: (action: Action) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        withContext(Dispatchers.Main.immediate) {
            action.collect(block)
        }
    }
}