package com.hits.core_ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTextStyles {

    val Header = TextStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )

    val SmallHeader = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    val FieldTitle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )

    val InputText = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    )

    val SmallText = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
}