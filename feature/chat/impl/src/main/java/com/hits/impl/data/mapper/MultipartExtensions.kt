package com.hits.impl.data.mapper

import android.content.Context
import androidx.core.net.toUri
import com.hits.api.model.Attachment
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun Context.uriToMultipart(
    attachment: Attachment
): MultipartBody.Part {

    val uri = attachment.uri.toUri()

    val inputStream =
        contentResolver.openInputStream(uri)
            ?: error("Can't open uri")

    val bytes = inputStream.readBytes()

    val requestBody =
        bytes.toRequestBody(
            "image/*".toMediaType()
        )

    return MultipartBody.Part.createFormData(
        "attachments",
        "photo.jpg",
        requestBody
    )
}