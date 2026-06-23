package com.hits.itindr.mainflow.profile.ui

import com.hits.core_ui.TagItem

data class EditProfileUiState(
    val isLoading: Boolean = false,

    val avatar: String? = null,

    val name: String = "",

    val about: String = "",

    val tags: List<TagItem> = emptyList(),

    val selectedIds: Set<String> = emptySet(),

    val isSaving: Boolean = false
)