package com.hits.itindr.mainflow.match.avatars

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hits.core_ui.R
import kotlin.math.roundToInt

@Composable
fun MatchAvatars(
    currentUserAvatar: String?,
    matchedAvatar: String?,
    leftOffset: Float,
    rightOffset: Float,
    borderAlpha: Float,
    lineProgress: Float,
) {
    Box(
        contentAlignment = Alignment.Center
    ){
        MatchConnectionLine(
            progress = lineProgress
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Avatar(
                avatar = currentUserAvatar, offsetX = leftOffset, borderAlpha = borderAlpha
            )

            Avatar(
                avatar = matchedAvatar, offsetX = rightOffset, borderAlpha = borderAlpha
            )
        }
    }
}

@Composable
private fun Avatar(
    avatar: String?,
    offsetX: Float,
    borderAlpha: Float,
) {

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    offsetX.roundToInt(), 0
                )
            }
            .size(132.dp), contentAlignment = Alignment.Center) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .border(
                    width = 3.dp, color = Color.White.copy(
                        alpha = borderAlpha * 0.35f
                    ), shape = CircleShape
                )
        )

        if (avatar == null) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.white_transparent30)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.avatar),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        } else {
            AsyncImage(
                model = avatar,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}