package za.co.bb.work_status.view.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.bb.core.domain.print
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.core.util.now
import za.co.bb.core.util.print
import za.co.bb.wages.domain.model.Wage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WageSelectionComponent(
    modifier: Modifier,
    wages: List<Wage>,
    horizontalPagerState: PagerState
) {
    Box(modifier = modifier) {
        Column {
            Text(
                text = "Wage Selection",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(start = 16.dp)
            )

            Carousel(
                modifier = Modifier.weight(1f),
                pagerState = horizontalPagerState
            ) { index ->
                Card(
                    modifier = Modifier.padding(8.dp),
                    backgroundColor = AppColors.current.surface,
                    shape = RoundedCornerShape(size = 12.dp),
                    elevation = 8.dp
                ) {
                    WageCarouselCardContent(
                        modifier = Modifier.padding(8.dp),
                        wage = wages[index]
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(horizontalPagerState.pageCount) { currentIndex ->
                Box(modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(
                        if (currentIndex == horizontalPagerState.settledPage) {
                            AppColors.current.primary
                        } else Color.Gray
                    )
                )
                if (currentIndex != horizontalPagerState.pageCount - 1) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Carousel(
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WageCarousel(
    modifier: Modifier,
    pagerState: PagerState,
    wages: List<Wage>
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        pageSpacing = 16.dp
    ) { index ->
        Card(
            modifier = Modifier.padding(8.dp),
            backgroundColor = AppColors.current.surface,
            shape = RoundedCornerShape(size = 12.dp),
            elevation = 8.dp
        ) {
            WageCarouselCardContent(
                modifier = Modifier.padding(8.dp),
                wage = wages[index]
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WageCarouselCardContent(
    modifier: Modifier,
    wage: Wage
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxSize(),
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
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Rate: ")
                Text(text = "ZAR ")
                Text(
                    text = wage.amount.print(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = (-12).dp),
            text = wage.amount.print(),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
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
            ),
            pagerState = rememberPagerState { 1 }
        )
    }
}