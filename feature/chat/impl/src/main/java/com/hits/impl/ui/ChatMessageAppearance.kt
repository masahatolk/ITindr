package com.hits.impl.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class ChatMessageAppearance(
    val backgroundColor: Color,
    val textColor: Color,
    val textSize: TextUnit,
    val textDateSize: TextUnit,
    val textWeight: FontWeight,
    val cornerRadius: Float,
)

interface ChatMessageAppearanceBuilder {
    fun setBackgroundColor(color: Color): ChatMessageAppearanceBuilder
    fun setTextColor(color: Color): ChatMessageAppearanceBuilder
    fun setTextSize(size: TextUnit): ChatMessageAppearanceBuilder
    fun setTextDateSize(size: TextUnit): ChatMessageAppearanceBuilder
    fun setTextWeight(weight: FontWeight): ChatMessageAppearanceBuilder
    fun setCornerRadius(radius: Float): ChatMessageAppearanceBuilder
    fun build(): ChatMessageAppearance
}

class DefaultChatMessageAppearanceBuilder : ChatMessageAppearanceBuilder {
    private var backgroundColor: Color = Color(0xFF2A2A2A)
    private var textColor: Color = Color.White
    private var textSize: TextUnit = 16.sp
    private var textDateSize: TextUnit = 12.sp
    private var textWeight: FontWeight = FontWeight.Normal
    private var cornerRadius: Float = 18f

    override fun setBackgroundColor(color: Color): ChatMessageAppearanceBuilder = apply {
        backgroundColor = color
    }

    override fun setTextColor(color: Color): ChatMessageAppearanceBuilder = apply {
        textColor = color
    }

    override fun setTextSize(size: TextUnit): ChatMessageAppearanceBuilder = apply {
        textSize = size
    }

    override fun setTextDateSize(size: TextUnit): ChatMessageAppearanceBuilder = apply {
        textDateSize = size
    }

    override fun setTextWeight(weight: FontWeight): ChatMessageAppearanceBuilder = apply {
        textWeight = weight
    }

    override fun setCornerRadius(radius: Float): ChatMessageAppearanceBuilder = apply {
        cornerRadius = radius
    }

    override fun build(): ChatMessageAppearance {
        return ChatMessageAppearance(
            backgroundColor = backgroundColor,
            textColor = textColor,
            textSize = textSize,
            textDateSize = textDateSize,
            textWeight = textWeight,
            cornerRadius = cornerRadius,
        )
    }
}

class ChatMessageAppearanceDirector {
    fun createIncomingAppearance(backgroundColor: Color): ChatMessageAppearance {
        return DefaultChatMessageAppearanceBuilder()
            .setBackgroundColor(backgroundColor)
            .setTextColor(Color.White)
            .setTextSize(16.sp)
            .setTextWeight(FontWeight.Normal)
            .setCornerRadius(16f)
            .build()
    }

    fun createOutgoingAppearance(backgroundColor: Color): ChatMessageAppearance {
        return DefaultChatMessageAppearanceBuilder()
            .setBackgroundColor(backgroundColor)
            .setTextColor(Color.White)
            .setTextSize(16.sp)
            .setTextWeight(FontWeight.Medium)
            .setCornerRadius(16f)
            .build()
    }
}
