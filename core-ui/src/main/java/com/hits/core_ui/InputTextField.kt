package com.hits.core_ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputTextField(
    modifier: Modifier,
    value: String,
    placeholderText: String,
    placeholderTextColor: Color,
    onValueChange: (String) -> Unit,
    minLines: Int,
    maxLines: Int,
    singleLine: Boolean,
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),

        shape = RoundedCornerShape(8.dp),

        placeholder = {
            Text(
                text = placeholderText,
                style = AppTextStyles.InputText,
                color = placeholderTextColor
            )
        },

        textStyle = TextStyle(
            color = Color.White, fontSize = 16.sp
        ),

        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.white_transparent20),
            unfocusedContainerColor = colorResource(R.color.white_transparent20),
            disabledContainerColor = colorResource(R.color.white_transparent20),

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,

            focusedPlaceholderColor = colorResource(R.color.white_transparent50),
            unfocusedPlaceholderColor = colorResource(R.color.white_transparent50),

            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.Gray,
        ),
        minLines = minLines,
        maxLines = maxLines,
        singleLine = singleLine,

        )
}