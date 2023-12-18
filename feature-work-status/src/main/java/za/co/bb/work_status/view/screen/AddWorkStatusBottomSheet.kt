package za.co.bb.work_status.view.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusEventHandler
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusScreenState
import za.co.bb.work_status.view.util.getAddWorkStatusViewModel

@Composable
private fun AddWorkStatusBottomSheet(
    uiState: AddWorkStatusScreenState,
    addWorkStatusEventHandler: AddWorkStatusEventHandler
) {

}

internal class AddWorkStatusBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            val addWorkStatusViewModel = getAddWorkStatusViewModel()
            val uiState by addWorkStatusViewModel.uiState.collectAsStateWithLifecycle()

            AddWorkStatusBottomSheet(
                uiState = uiState,
                addWorkStatusEventHandler = addWorkStatusViewModel.addWorkStatusEventHandler
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}