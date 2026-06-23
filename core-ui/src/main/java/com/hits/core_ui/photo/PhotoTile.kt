package com.hits.core_ui.photo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import coil.compose.AsyncImage
import com.hits.core_ui.R

@Composable
fun PhotoTile(
    photo: GalleryPhoto,
    selected: Boolean,
    onClick: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(onClick = {})
    ) {

        AsyncImage(
            model = photo.uri,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .matchParentSize()
                .background(colorResource(R.color.black_transparent50))
        )

        Checkbox(
            modifier = Modifier
                .clip(CircleShape),
            checked = selected,
            onCheckedChange = onClick,
            enabled = true,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.White,
                checkmarkColor = colorResource(R.color.purple),
                uncheckedColor = colorResource(R.color.white_transparent50)
            )
        )
    }
}