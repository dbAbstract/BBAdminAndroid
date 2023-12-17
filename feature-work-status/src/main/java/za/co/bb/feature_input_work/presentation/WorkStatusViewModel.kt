package za.co.bb.feature_input_work.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import za.co.bb.core.presentation.BaseViewModel
import za.co.bb.work_hours.domain.WorkHoursRepository

internal class WorkStatusViewModel(
    private val workHoursRepository: WorkHoursRepository,
) : BaseViewModel<WorkStatusAction>() {
    private val _uiState = MutableStateFlow(WorkStatusScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    init {

    }

    val workStatusEventHandler = object : WorkStatusEventHandler {
        override fun onBack() {
            emitAction(WorkStatusAction.NavigateBack)
        }
    }
}