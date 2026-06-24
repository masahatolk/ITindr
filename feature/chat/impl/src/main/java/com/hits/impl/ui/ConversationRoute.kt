package com.hits.impl.ui

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hits.core_media.camera.createTempImageUri
import com.hits.core_media.gallery.saveImageToGallery
import com.hits.core_media.permission.galleryPermission
import com.hits.core_media.ui.PhotoPickerViewModel
import com.hits.core_ui.photo.PhotoPickerBottomSheet
import com.hits.impl.ui.components.ChatInput
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConversationRoute(
    chatId: String,
    title: String,
    onBack: () -> Unit,
    viewModel: ChatViewModel = koinViewModel(),
) {
    val pickerViewModel = koinViewModel<PhotoPickerViewModel>()
    val pickerState by pickerViewModel.state.collectAsStateWithLifecycle()

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var showPicker by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    var cameraUri by remember {
        mutableStateOf(createTempImageUri(context))
    }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->

        if (success) {

            val galleryUri = saveImageToGallery(context, cameraUri)

            pickerViewModel.reloadPhotos()

            pickerViewModel.togglePhoto(
                uri = galleryUri, multiSelect = true, maxSelection = 5
            )

            showPicker = true
        }
    }

    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->

        if (granted) {

            pickerViewModel.loadPhotos()

            showPicker = true
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->

        if (granted) {

            cameraUri = createTempImageUri(context)

            takePhotoLauncher.launch(cameraUri)
        }
    }

    if (showPicker) {

        PhotoPickerBottomSheet(
            state = pickerState,

            onPhotoClick = {
                pickerViewModel.togglePhoto(
                    uri = it, multiSelect = true, maxSelection = 5
                )
            },

            onCameraClick = {
                cameraPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            },

            onDismiss = {
                pickerViewModel.clearSelection()

                showPicker = false
            },

            currentElement = {
                ChatInput(
                    value = state.messageDraft, onValueChange = {
                        viewModel.onIntent(
                            ChatIntent.MessageDraftChanged(it)
                        )
                    }, onSend = {
                        viewModel.sendMessageWithPhotos(
                            text = state.messageDraft,
                            photos = pickerState.selectedPhotos.map { it.toString() })

                        pickerViewModel.clearSelection()

                        showPicker = false
                    }, onAttachPhoto = {}, isSending = state.isSendingMessage
                )
            },
        )
    }

    LaunchedEffect(chatId) {
        viewModel.onIntent(
            ChatIntent.LoadMessages(chatId)
        )
    }

    ConversationScreen(
        state = state,
        title = title,
        onBack = onBack,
        onOpenAttachmentPicker = {

            pickerViewModel.clearSelection()

            galleryPermissionLauncher.launch(
                galleryPermission()
            )
        },
        onDraftChanged = {
            viewModel.onIntent(
                ChatIntent.MessageDraftChanged(it)
            )
        },
        onSend = {
            viewModel.onIntent(
                ChatIntent.SendMessage
            )
        },
    )
}