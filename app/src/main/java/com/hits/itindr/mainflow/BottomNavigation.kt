package com.hits.itindr.mainflow

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hits.core_ui.R

@Composable
fun BottomNavigation(
    selectedRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val bottomItems = listOf(
        BottomItem(
            Screen.Feed.route,
            R.drawable.feed,
            "Поток"
        ),
        BottomItem(
            Screen.People.route,
            R.drawable.people,
            "Люди"
        ),
        BottomItem(
            Screen.ChatList.route,
            R.drawable.chat,
            "Чаты"
        ),
        BottomItem(
            Screen.Profile.route,
            R.drawable.profile,
            "Профиль"
        ),
    )

    SubcomposeLayout(
        modifier = modifier
            .padding(bottom = 24.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(colorResource(id = R.color.bottom_nav_gray))
            .padding(8.dp),
    ) { constraints ->

        val widths = bottomItems.indices.map { selected ->

            val placeable = subcompose("case_$selected") {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    bottomItems.forEachIndexed { index, item ->

                        NavItem(
                            icon = item.icon,
                            label = item.label,
                            isSelected = index == selected,
                            onClick = {}
                        )
                    }
                }
            }
                .first()
                .measure(constraints)

            placeable.width
        }

        val maxWidth = widths.max()

        val finalPlaceable = subcompose("final") {

            Row(
                modifier = Modifier.width(
                    with(density) { maxWidth.toDp() }
                ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                bottomItems.forEach { item ->

                    NavItem(
                        icon = item.icon,
                        label = item.label,
                        isSelected = selectedRoute == item.route,
                        onClick = {
                            onNavigate(item.route)
                        }
                    )
                }
            }
        }
            .first()
            .measure(constraints)

        layout(
            finalPlaceable.width,
            finalPlaceable.height
        ) {
            finalPlaceable.place(0, 0)
        }
    }
}

@Composable
fun NavItem(
    icon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else colorResource(id = R.color.bottom_nav_gray),
        label = ""
    )

    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else Color.White,
        label = ""
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(bgColor)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = contentColor
        )

        if (isSelected) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
        }
    }
}

data class BottomItem(
    val route: String,
    val icon: Int,
    val label: String,
)