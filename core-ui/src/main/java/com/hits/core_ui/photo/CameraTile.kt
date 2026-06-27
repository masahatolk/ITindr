package com.hits.core_ui.photo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hits.core_ui.R

@Composable
fun CameraTile(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick)
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {

        Icon(
            modifier = Modifier
                .size(52.dp),
            painter = painterResource(R.drawable.ic_camera),
            tint = Color.White,
            contentDescription = null
        )
    }
}