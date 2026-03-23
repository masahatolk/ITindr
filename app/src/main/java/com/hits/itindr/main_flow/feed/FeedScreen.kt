package com.hits.itindr.main_flow.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hits.itindr.R

@Composable
fun FeedScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally

        ) {
        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            tint = Color.White,
        )



        /*ProfileCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),

            name = "Андрей Иванов",
            tags = listOf("Python", "Django", "REST"),
            description = "Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.",
            imageUrl = "https://picsum.photos/400/800",

            onLike = {
                println("LIKE")
            },
            onDislike = {
                println("DISLIKE")
            }
        )*/
    }
}

