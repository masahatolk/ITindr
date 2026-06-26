package com.hits.impl.ui.swipeableCards.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hits.core_ui.R

@Composable
fun ReactionPanel(
    onLike : () -> Unit,
    onDislike : () -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CardActionButton(
            color = colorResource(R.color.red),
            icon = painterResource(id = R.drawable.close),
            onClick = onDislike,
            modifier = Modifier.weight(1f),
        )

        CardActionButton(
            color = colorResource(R.color.green),
            icon = painterResource(id = R.drawable.like),
            onClick = onLike,
            modifier = Modifier.weight(1f),
        )
    }
}