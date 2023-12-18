package za.co.bb.work_status.presentation.add_work_status

internal sealed interface AddWorkStatusAction {
    data object ShowConfirmation : AddWorkStatusAction
}