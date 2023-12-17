package za.co.bb.feature_input_work.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import za.co.bb.feature_input_work.presentation.WorkStatusViewModel
import za.co.bb.work_hours.di.WorkHoursDependencyContainer

private class InputWorkViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkStatusViewModel::class.java)) {
            return WorkStatusViewModel(
                workHoursRepository = WorkHoursDependencyContainer.workHoursRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
internal fun getWorkStatusViewModel(): WorkStatusViewModel {
    return viewModel(
        factory = InputWorkViewModelFactory()
    )
}