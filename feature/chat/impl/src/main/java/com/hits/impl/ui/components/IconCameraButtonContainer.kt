package com.hits.impl.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hits.core_ui.R

@Composable
fun IconCameraButtonContainer(
    icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(54.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
        color = Color.Transparent,
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(R.color.light_gray)
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                tint = colorResource(R.color.light_gray),
                modifier = Modifier.size(32.dp),
                contentDescription = null
            )
        }
    }
}