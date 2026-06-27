package com.hits.impl.ui.match.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hits.core_ui.ActionButton
import com.hits.core_ui.R

@Composable
fun MatchMessageButton(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onClick: () -> Unit,
) {

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight ->
                fullHeight
            }
        ) + fadeIn()
    ) {

        ActionButton(
            onClick = onClick,
            text = stringResource(R.string.write_message),
            enabled = true,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        )
    }
}