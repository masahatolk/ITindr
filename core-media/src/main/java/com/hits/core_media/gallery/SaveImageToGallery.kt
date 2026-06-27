package com.hits.core_media.gallery

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

fun saveImageToGallery(
    context: Context,
    sourceUri: Uri
): Uri {

    val resolver = context.contentResolver

    val contentValues = ContentValues().apply {

        put(
            MediaStore.Images.Media.DISPLAY_NAME,
            "IMG_${System.currentTimeMillis()}.jpg"
        )

        put(
            MediaStore.Images.Media.MIME_TYPE,
            "image/jpeg"
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                "Pictures"
            )

            put(
                MediaStore.Images.Media.IS_PENDING,
                1
            )
        }
    }

    val galleryUri =
        resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
            ?: throw IllegalStateException(
                "Cannot create MediaStore record"
            )

    resolver.openInputStream(sourceUri)?.use { input ->

        resolver.openOutputStream(galleryUri)?.use { output ->

            input.copyTo(output)
        }
    } ?: throw IllegalStateException(
        "Cannot read camera image"
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

        contentValues.clear()

        contentValues.put(
            MediaStore.Images.Media.IS_PENDING,
            0
        )

        resolver.update(
            galleryUri,
            contentValues,
            null,
            null
        )
    }

    return galleryUri
}