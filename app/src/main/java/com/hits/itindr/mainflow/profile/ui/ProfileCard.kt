package com.hits.itindr.mainflow.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hits.core_ui.AppTextStyles
import com.hits.core_ui.R
import com.hits.itindr.mainflow.feed.swipeableCards.ui.ProfileTagsFlow
import com.hits.itindr.mainflow.profile.domain.Profile

@Composable
fun ProfileCard(profile: Profile, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .heightIn(min = 100.dp, max = 340.dp)
                .clip(RoundedCornerShape(32.dp)),
            contentAlignment = Alignment.BottomStart
        ) {
            if (profile.avatar != null) {
                AsyncImage(
                    model = profile.avatar,
                    contentDescription = profile.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.white_transparent30)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.avatar),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(24.dp),
                text = profile.name,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(Modifier.height(24.dp))

        ProfileTagsFlow(
            tags = profile.topics.map { it.title },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = profile.about,
            color = Color.White,
            style = AppTextStyles.InputText,
        )
    }
}