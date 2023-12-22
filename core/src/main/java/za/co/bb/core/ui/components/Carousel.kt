package za.co.bb.core.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(
    modifier: Modifier,
    pagerState: PagerState,
    entitySpacing: Dp = 16.dp,
    content: @Composable (index: Int) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        pageSpacing = entitySpacing
    ) { index ->
        content(index)
    }
}