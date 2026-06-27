package com.hits.core_ui.photo

import android.net.Uri
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoPickerBottomSheet(
    state: PhotoPickerUiState,
    onPhotoClick: (Uri) -> Unit,
    onCameraClick: () -> Unit,
    onDismiss: () -> Unit,
    currentElement: @Composable () -> Unit,
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

                val selected = photo.uri in state.selectedPhotos

                PhotoTile(
                    photo = photo,
                    selected = selected,
                    onClick = { onPhotoClick(photo.uri) },
                )
            }
        }

        currentElement()
    }
}