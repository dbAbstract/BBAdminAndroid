package za.co.bb.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Action> : ViewModel() {
    private val _action = Channel<Action>()
    open val action = _action.receiveAsFlow()

    fun emitAction(action: Action) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _action.send(action)
        }
    }
}