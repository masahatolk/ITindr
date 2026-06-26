package com.hits.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hits.core_ui.ActionButton
import com.hits.core_ui.AppTextStyles
import com.hits.core_ui.AvatarAssistChip
import com.hits.core_ui.FullScreenLoader
import com.hits.core_ui.InputTextField
import com.hits.core_ui.R
import com.hits.core_ui.TopicFlow
import com.hits.core_ui.Toolbar

@Composable
fun EditProfileScreen(
    state: EditProfileUiState,
    title: String,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onChangeAvatarClick: () -> Unit,
    onDeleteAvatarClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onAboutChange: (String) -> Unit,
    onTopicsChanged: (Set<String>) -> Unit
) {

    val avatarModel =
        when {
            state.avatarDeleted -> null
            state.localAvatarUri != null -> state.localAvatarUri
            else -> state.remoteAvatar
        }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        containerColor = Color.Transparent,

        topBar = {
            Toolbar(
                title = title,
                onBack = onBack
            )
        },

        bottomBar = {
            ActionButton(
                text = stringResource(R.string.save_button_text),
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                enabled = true,
            )
        }
    ) { innerPadding ->
        when {
            state.isLoading -> FullScreenLoader("Загружаем профиль")
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (avatarModel != null) {
                            AsyncImage(
                                model = avatarModel,
                                contentDescription = state.name,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(24.dp)),
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(colorResource(R.color.white_transparent30)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.avatar),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            AvatarAssistChip(
                                onClick = onChangeAvatarClick,
                                text = stringResource(if (avatarModel == null) R.string.choose_photo else R.string.change_photo),
                                icon = painterResource(R.drawable.gallery_add)
                            )

                            if (avatarModel != null) {
                                AvatarAssistChip(
                                    onClick = onDeleteAvatarClick,
                                    text = stringResource(R.string.delete_photo),
                                    icon = painterResource(R.drawable.trash_bin)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = stringResource(R.string.name),
                        style = AppTextStyles.FieldTitle,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    InputTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = state.name,
                        onValueChange = onNameChange,
                        placeholderText = stringResource(R.string.name_input_layout_text),
                        placeholderTextColor = colorResource(R.color.white_transparent50),
                        singleLine = true,
                        minLines = 1,
                        maxLines = 1,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.additional_info),
                        style = AppTextStyles.FieldTitle,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    InputTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = state.about,
                        onValueChange = onAboutChange,
                        minLines = 6,
                        maxLines = 6,
                        placeholderText = stringResource(R.string.additional_info_input_layout_text),
                        placeholderTextColor = colorResource(R.color.white_transparent50),
                        singleLine = false
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.interests),
                        style = AppTextStyles.FieldTitle,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TopicFlow(
                        tags = state.tags,
                        selectedIds = state.selectedIds,
                        multiSelect = true,
                        maxSelected = 100,
                        onSelectionChange = onTopicsChanged,
                    )
                }
            }
        }
    }
}