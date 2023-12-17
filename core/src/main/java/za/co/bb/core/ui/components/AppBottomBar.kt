package za.co.bb.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import za.co.bb.core.navigation.Screen

@Composable
fun AppBottomBar(
    modifier: Modifier,
    onNavIconClick: (Screen) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = {
                    onNavIconClick(Screen.HomeScreen)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null
                )
            }

            IconButton(
                onClick = {
                    onNavIconClick(Screen.InputWorkHours)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null
                )
            }
        }
    }
}

const val BOTTOM_BAR_HEIGHT = 80