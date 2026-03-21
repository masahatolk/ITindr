package com.hits.itindr

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigation(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        R.drawable.feed,
        R.drawable.people,
        R.drawable.chat,
        R.drawable.profile
    )

    val labels = listOf("Поток", "Люди", "Чаты", "Профиль")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(48.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(colorResource(id = R.color.bottom_nav_gray))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEachIndexed { index, iconRes ->
            val isSelected = index == selectedIndex

            val bgColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else colorResource(id = R.color.bottom_nav_gray),
                label = "bg"
            )

            val contentColor by animateColorAsState(
                targetValue = if (isSelected) Color.Black else Color.White,
                label = "content"
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(bgColor)
                    .clickable { onItemSelected(index) }
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .animateContentSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = contentColor
                )

                if (isSelected) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = labels[index],
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
