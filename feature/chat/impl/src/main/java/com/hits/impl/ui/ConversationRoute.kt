package com.hits.impl.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hits.impl.ui.components.attachment.AttachmentPickerBottomSheet
import com.hits.impl.ui.components.attachment.MediaPickerCoordinator
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConversationRoute(
    chatId: String,
    title: String,
    onBack: () -> Unit,
    viewModel: ChatViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(chatId) {
        viewModel.onIntent(
            ChatIntent.LoadMessages(chatId)
        )
    }



    MediaPickerCoordinator(

        onPhotosSelected = { uris ->

            viewModel.onIntent(
                ChatIntent.AddAttachments(
                    uris.map(Uri::toString)
                )
            )
        },

        onCameraPhotoTaken = { uri ->

            viewModel.onIntent(
                ChatIntent.AddAttachments(
                    listOf(uri.toString())
                )
            )
        }
    ) { openGallery, openCamera ->

        ConversationScreen(
            state = state,
            title = title,
            onBack = onBack,
            onOpenAttachmentPicker = {
                viewModel.onIntent(
                    ChatIntent.OpenAttachmentPicker
                )
            },
            onRemoveAttachment = {
                viewModel.onIntent(
                    ChatIntent.RemoveAttachment(it)
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
            }
        )

        if (state.isAttachmentPickerVisible) {

            AttachmentPickerBottomSheet(

                onGalleryClick = {

                    openGallery()

                    viewModel.onIntent(
                        ChatIntent.CloseAttachmentPicker
                    )
                },

                onCameraClick = {

                    openCamera()

                    viewModel.onIntent(
                        ChatIntent.CloseAttachmentPicker
                    )
                },

                onDismiss = {

                    viewModel.onIntent(
                        ChatIntent.CloseAttachmentPicker
                    )
                }
            )
        }
    }
}