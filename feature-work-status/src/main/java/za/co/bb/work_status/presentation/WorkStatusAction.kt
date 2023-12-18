package za.co.bb.work_status.presentation

internal sealed interface WorkStatusAction {
    data object NavigateBack : WorkStatusAction
    data object NavigateToAddWorkStatus : WorkStatusAction
    data class ShowError(val message: String) : WorkStatusAction
}