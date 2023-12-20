package za.co.bb.work_status.view.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.bb.core.domain.print
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.core.util.now
import za.co.bb.core.util.print
import za.co.bb.wages.domain.model.Wage

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun WageCarousel(
    modifier: Modifier,
    wages: List<Wage>
) {
    val horizontalPagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0.0f,
        pageCount = wages::size
    )

    Card(
        modifier = modifier,
        backgroundColor = AppColors.current.surface,
        shape = RoundedCornerShape(size = 12.dp),
        elevation = 8.dp
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = horizontalPagerState,
            pageSpacing = 16.dp
        ) { index ->
            WageCarouselEntity(wage = wages[index])
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WageCarouselEntity(
    wage: Wage
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Description: ")
            Text(
                text = wage.description,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.basicMarquee()
            )
        }

        Row {
            Text(text = "Created: ")
            Text(
                text = wage.issueDate.print(),
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
        Row(
            modifier = Modifier.padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ZAR ",
                modifier = Modifier.padding(top = 13.dp)
            )
            Text(
                text = wage.amount.print(),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        WageCarousel(
            modifier = Modifier
                .padding(top = 16.dp)
                .height(120.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            wages = listOf(
                Wage(
                    id = "",
                    description = "Base wage",
                    issueDate = now,
                    amount = 23.5
                )
            )
        )
    }
}