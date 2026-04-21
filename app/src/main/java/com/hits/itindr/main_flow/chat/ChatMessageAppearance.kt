package com.hits.itindr.main_flow.chat

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.hits.itindr.R
import io.github.kakaocup.kakao.common.utilities.getResourceColor

data class ChatMessageAppearance(
    val backgroundColor: Color,
    val textColor: Color,
    val textSize: TextUnit,
    val textWeight: FontWeight,
    val cornerRadius: Float,
)

interface ChatMessageAppearanceBuilder {
    fun setBackgroundColor(color: Color): ChatMessageAppearanceBuilder
    fun setTextColor(color: Color): ChatMessageAppearanceBuilder
    fun setTextSize(size: TextUnit): ChatMessageAppearanceBuilder
    fun setTextWeight(weight: FontWeight): ChatMessageAppearanceBuilder
    fun setCornerRadius(radius: Float): ChatMessageAppearanceBuilder
    fun build(): ChatMessageAppearance
}

class DefaultChatMessageAppearanceBuilder : ChatMessageAppearanceBuilder {
    private var backgroundColor: Color = Color(0xFF2A2A2A)
    private var textColor: Color = Color.White
    private var textSize: TextUnit = 16.sp
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
            textWeight = textWeight,
            cornerRadius = cornerRadius,
        )
    }
}

class ChatMessageAppearanceDirector {
    fun createIncomingAppearance(): ChatMessageAppearance {
        return DefaultChatMessageAppearanceBuilder()
            .setBackgroundColor(Color(getResourceColor(R.color.bottom_nav_gray)))
            .setTextColor(Color.White)
            .setTextSize(16.sp)
            .setTextWeight(FontWeight.Normal)
            .setCornerRadius(16f)
            .build()
    }

    fun createOutgoingAppearance(): ChatMessageAppearance {
        return DefaultChatMessageAppearanceBuilder()
            .setBackgroundColor(Color(getResourceColor(R.color.purple_bubble)))
            .setTextColor(Color.White)
            .setTextSize(16.sp)
            .setTextWeight(FontWeight.Medium)
            .setCornerRadius(16f)
            .build()
    }
}
