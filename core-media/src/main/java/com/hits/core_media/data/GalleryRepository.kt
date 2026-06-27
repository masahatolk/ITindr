package com.hits.core_media.data

import com.hits.core_ui.photo.GalleryPhoto

interface GalleryRepository {

    suspend fun loadPhotos(): List<GalleryPhoto>
}