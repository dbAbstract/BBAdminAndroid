package za.co.bb.feature_input_work.presentation

internal sealed interface WorkStatusAction {
    data object NavigateBack : WorkStatusAction
    data class ShowError(val message: String) : WorkStatusAction
}