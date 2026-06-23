package com.hits.impl.ui.components.attachment

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun MediaPickerCoordinator(
    onPhotosSelected: (List<Uri>) -> Unit,
    onCameraPhotoTaken: (Uri) -> Unit,
    content: @Composable (
        openGallery: () -> Unit,
        openCamera: () -> Unit
    ) -> Unit
) {

    val context = LocalContext.current

    var cameraUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.PickMultipleVisualMedia(
                    maxItems = 5
                )
        ) { uris ->

            if (uris.isNotEmpty()) {
                onPhotosSelected(uris)
            }
        }

    val takePictureLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->

            if (success) {

                cameraUri?.let {
                    onCameraPhotoTaken(it)
                }
            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {

                cameraUri?.let {
                    takePictureLauncher.launch(it)
                }
            }
        }

    fun openGallery() {

        galleryLauncher.launch(
            PickVisualMediaRequest(
                ActivityResultContracts
                    .PickVisualMedia
                    .ImageOnly
            )
        )
    }

    fun openCamera() {

        val uri = createImageUri(context)

        cameraUri = uri

        cameraPermissionLauncher.launch(
            Manifest.permission.CAMERA
        )
    }

    content(
        ::openGallery,
        ::openCamera
    )
}


fun createImageUri(context: Context): Uri {
    val file = File(
        context.cacheDir,
        "photo_${System.currentTimeMillis()}.jpg"
    )

    file.createNewFile()

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}