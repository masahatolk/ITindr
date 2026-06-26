package com.hits.impl.ui

import android.net.Uri
import com.hits.core_ui.TagItem

data class EditProfileUiState(
    val isLoading: Boolean = false,

    val remoteAvatar: String? = null,

    val localAvatarUri: Uri? = null,

    val avatarDeleted: Boolean = false,

    val name: String = "",

    val about: String = "",

    val tags: List<TagItem> = emptyList(),

    val selectedIds: Set<String> = emptySet(),

    val isSaving: Boolean = false,
)