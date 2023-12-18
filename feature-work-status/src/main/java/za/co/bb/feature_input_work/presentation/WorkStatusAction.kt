package za.co.bb.feature_input_work.presentation

internal sealed interface WorkStatusAction {
    data object NavigateBack : WorkStatusAction
}