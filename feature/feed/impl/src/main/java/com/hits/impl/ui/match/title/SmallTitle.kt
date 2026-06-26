package com.hits.impl.ui.match.title

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.hits.core_ui.AppTextStyles
import com.hits.core_ui.R

@Composable
fun SmallTitle(
    alpha: Float,
) {

    Text(
        modifier = Modifier
            .alpha(alpha),
        style = AppTextStyles.FieldTitle,
        textAlign = TextAlign.Center,
        color = Color.White,
        text = stringResource(R.string.interfaces_matched)
    )
}