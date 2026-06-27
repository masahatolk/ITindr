package com.hits.impl.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hits.impl.R

@Composable
fun UserAvatar(avatar: String?) {
    AsyncImage(
        model = avatar,
        contentDescription = null,
        placeholder = painterResource(R.drawable.default_profile_icon),
        error = painterResource(R.drawable.default_profile_icon),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
    )
}