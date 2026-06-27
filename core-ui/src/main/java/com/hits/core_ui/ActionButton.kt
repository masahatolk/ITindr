package com.hits.core_ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonColors(
            containerColor = Color.White,
            contentColor = Color.Transparent,
            disabledContainerColor = colorResource(R.color.white_transparent50),
            disabledContentColor = Color.Transparent
        ),
        enabled = enabled,
    ) {
        Text(
            text = text,
            style = AppTextStyles.FieldTitle,
            color = Color.Black
        )
    }
}