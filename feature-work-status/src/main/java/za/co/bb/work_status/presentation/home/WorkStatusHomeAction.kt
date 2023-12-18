package za.co.bb.work_status.presentation.home

internal sealed interface WorkStatusHomeAction {
    data object NavigateBack : WorkStatusHomeAction
    data object NavigateToAddWorkStatus : WorkStatusHomeAction
    data class ShowError(val message: String) : WorkStatusHomeAction
}