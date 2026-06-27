package com.hits.core_media.data

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.hits.core_ui.photo.GalleryPhoto

class GalleryRepositoryImpl(
    private val context: Context
) : GalleryRepository {

    override suspend fun loadPhotos(): List<GalleryPhoto> {

        val result = mutableListOf<GalleryPhoto>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID
        )

        val sortOrder =
            "${MediaStore.Images.Media.DATE_ADDED} DESC"

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->

            val idColumn =
                cursor.getColumnIndexOrThrow(
                    MediaStore.Images.Media._ID
                )

            while (cursor.moveToNext()) {

                val id =
                    cursor.getLong(idColumn)

                val uri =
                    ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                result += GalleryPhoto(uri)
            }
        }

        return result
    }
}