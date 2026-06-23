package com.hits.impl.ui.components.attachment

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentPickerBottomSheet(
    onGalleryClick: () -> Unit, onCameraClick: () -> Unit, onDismiss: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {

        TextButton(
            onClick = onCameraClick
        ) {
            Text("Камера")
        }

        TextButton(
            onClick = onGalleryClick
        ) {
            Text("Галерея")
        }
    }
}