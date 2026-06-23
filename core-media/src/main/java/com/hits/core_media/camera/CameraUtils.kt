package com.hits.core_media.camera

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun createTempImageUri(
    context: Context
): Uri {

    val file = File.createTempFile(
        "avatar_",
        ".jpg",
        context.cacheDir
    )

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}