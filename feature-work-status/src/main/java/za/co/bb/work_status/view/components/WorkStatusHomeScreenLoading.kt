package za.co.bb.work_status.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import za.co.bb.core.ui.components.AppTopBar
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.feature_work_status.R

@Composable
internal fun WorkStatusHomeScreenLoading(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .background(AppColors.current.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        AppTopBar(
            headerText = stringResource(id = R.string.work_status_header),
            onBack = onBack
        )
        Spacer(modifier = Modifier.weight(1f))
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = AppColors.current.primary
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}