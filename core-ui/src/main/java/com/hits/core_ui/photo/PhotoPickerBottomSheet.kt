package com.hits.core_ui.photo

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hits.core_ui.ActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoPickerBottomSheet(
    state: PhotoPickerUiState,
    onPhotoClick: (Uri) -> Unit,
    onCameraClick: () -> Unit,
    onUsePhotoClick: () -> Unit,
    onDismiss: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(3)
        ) {
            item {

                CameraTile(
                    onClick = onCameraClick
                )
            }

            items(
                state.photos
            ) { photo ->

                val selected = photo.uri == state.selectedPhoto

                PhotoTile(
                    photo = photo,
                    selected = selected,
                    onClick = { onPhotoClick(photo.uri) },
                )
            }
        }

        ActionButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Использовать фото",
            enabled = state.selectedPhoto != null,
            onClick = onUsePhotoClick
        )
    }
}