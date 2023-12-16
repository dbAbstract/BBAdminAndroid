package za.co.bb.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import za.co.bb.home.domain.usecase.GetWageStatusForEmployees

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