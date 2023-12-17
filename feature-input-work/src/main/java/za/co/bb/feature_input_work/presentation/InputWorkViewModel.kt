package za.co.bb.feature_input_work.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import za.co.bb.core.presentation.BaseViewModel
import za.co.bb.work_hours.domain.WorkHoursRepository

internal class InputWorkViewModel(
    private val workHoursRepository: WorkHoursRepository,
) : BaseViewModel<InputWorkAction>() {
    private val _uiState = MutableStateFlow(InputWorkScreenState())
    val uiState = _uiState.asStateFlow()

    init {

    }
}