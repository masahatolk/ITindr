package com.hits.core_ui.photo

import android.net.Uri

data class PhotoPickerUiState(

    val isLoading: Boolean = false,

    val photos: List<GalleryPhoto> = emptyList(),

    val selectedPhotos: Set<Uri> = emptySet(),
)
