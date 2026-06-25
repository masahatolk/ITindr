package com.hits.itindr.mainflow.match.title

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hits.core_ui.R


@Composable
fun MatchTitle(
    visibleLetters: Int,
    glowAlpha: Float,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        symbols.forEachIndexed { index, symbol ->

            if (symbol.drawable == null) {

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

            } else {
                Spacer(
                    modifier = Modifier.width(2.dp)
                )

                MatchLetter(
                    drawable = symbol.drawable,
                    glowDrawable = symbol.glowDrawable,
                    visible = index < visibleLetters,
                    glowAlpha = glowAlpha
                )
            }
        }
    }
}

private data class TitleSymbol(
    val drawable: Int?,
    val glowDrawable: Int?,
)

private val symbols = listOf(

    TitleSymbol(
        R.drawable.letter_i,
        R.drawable.glow_letter_i,
    ),
    TitleSymbol(
        R.drawable.letter_t,
        R.drawable.glow_letter_t,
    ),
    TitleSymbol(
        R.drawable.comma,
        R.drawable.glow_comma,
    ),
    TitleSymbol(
        R.drawable.letter_s,
        R.drawable.glow_letter_s,
    ),

    TitleSymbol(null, null),

    TitleSymbol(
        R.drawable.letter_a,
        R.drawable.glow_letter_a,
    ),

    TitleSymbol(null, null),

    TitleSymbol(
        R.drawable.letter_m,
        R.drawable.glow_letter_m,
    ),
    TitleSymbol(
        R.drawable.letter_a,
        R.drawable.glow_letter_a,
    ),
    TitleSymbol(
        R.drawable.letter_t,
        R.drawable.glow_letter_t,
    ),
    TitleSymbol(
        R.drawable.letter_c,
        R.drawable.glow_letter_c,
    ),
    TitleSymbol(
        R.drawable.letter_h,
        R.drawable.glow_letter_h,
    ),

    TitleSymbol(
        R.drawable.exclamation_point,
        R.drawable.glow_exclamation_point,
    ),
)