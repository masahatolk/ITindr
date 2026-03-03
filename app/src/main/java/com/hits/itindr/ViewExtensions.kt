package com.hits.itindr

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun View.applyStatusBarPadding() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
        v.updatePadding(top = statusBarHeight)
        insets
    }
}
