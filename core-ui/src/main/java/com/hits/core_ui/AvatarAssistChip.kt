package com.hits.core_ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun AvatarAssistChip(
    onClick: () -> Unit,
    text: String,
    icon: Painter,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        colors = ButtonColors(
            containerColor = colorResource(R.color.white_transparent20),
            contentColor = Color.Transparent,
            disabledContainerColor = colorResource(R.color.white_transparent20),
            disabledContentColor = Color.Transparent
        ),
    ) {
        Icon(
            painter = icon,
            tint = Color.White,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = AppTextStyles.SmallText,
            color = Color.White,
        )
    }
}