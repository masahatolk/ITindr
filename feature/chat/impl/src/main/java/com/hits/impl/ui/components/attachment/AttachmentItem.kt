package com.hits.impl.ui.components.attachment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hits.api.model.Attachment
import com.hits.core_ui.R

@Composable
fun AttachmentItem(
    attachment: Attachment,
    onRemove: () -> Unit
) {

    Box {

        AsyncImage(
            model = attachment.uri,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = onRemove,
            modifier = Modifier.align(
                Alignment.TopEnd
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.close),
                contentDescription = null
            )
        }
    }
}