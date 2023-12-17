package za.co.bb.home.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import za.co.bb.home.di.DependencyContainer
import za.co.bb.home.domain.usecase.GetWageStatusForEmployees
import za.co.bb.home.presentation.HomeScreenViewModel

internal class HomeScreenViewModelFactory(
    private val getWageStatusForEmployees: GetWageStatusForEmployees
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(
                getWageStatusForEmployees = getWageStatusForEmployees
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
internal fun getHomeScreenViewModel(): HomeScreenViewModel {
    return viewModel<HomeScreenViewModel>(
        factory = HomeScreenViewModelFactory(
            getWageStatusForEmployees = DependencyContainer.getWageStatusForEmployees
        )
    )
}