package com.hits.core_media.extensions

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun Uri.toAvatarPart(
    context: Context
): MultipartBody.Part {

    val bytes = context.contentResolver.openInputStream(this)?.use { it.readBytes() }
        ?: throw IllegalArgumentException(
            "Cannot read uri: $this"
        )

    val mimeType = context.contentResolver.getType(this) ?: "image/jpeg"

    val body = bytes.toRequestBody(
        mimeType.toMediaType()
    )

    return MultipartBody.Part.createFormData(
        name = "avatar", filename = "avatar_${System.currentTimeMillis()}.jpg", body = body
    )
}