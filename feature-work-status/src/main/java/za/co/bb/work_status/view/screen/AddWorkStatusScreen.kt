package za.co.bb.work_status.view.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import za.co.bb.core.ui.components.AppTopBar
import za.co.bb.wages.domain.model.Wage
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusEventHandler
import za.co.bb.work_status.presentation.add_work_status.AddWorkStatusScreenState

@Composable
internal fun AddWorkStatusScreen(
    uiState: AddWorkStatusScreenState,
    addWorkStatusEventHandler: AddWorkStatusEventHandler
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTopBar(
            headerText = uiState.employee?.let { employee ->
                employee.firstName + " " + employee.surname
            } ?: "",
            onBack = addWorkStatusEventHandler::navigateBack,
        )
        WageCarousel(wages = uiState.wages)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WageCarousel(
    wages: List<Wage>
) {
    val horizontalPagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0.0f,
        pageCount = wages::size
    )
    HorizontalPager(state = horizontalPagerState) {

    }
}