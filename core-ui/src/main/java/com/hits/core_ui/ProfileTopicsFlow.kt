package com.hits.core_ui

import android.view.ContextThemeWrapper
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.hits.api.model.Topic
import com.hits.core_ui.R.style.SwipeableProfileTagFlowView

@Composable
fun ProfileTopicsFlow(
    topics: List<Topic>,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TopicFlowView(ContextThemeWrapper(context, SwipeableProfileTagFlowView)).apply {
                setOnTouchListener { _, _ -> true }
                isClickable = false
                isFocusable = false
            }
        },
        update = { view ->
            view.tags = topics.map { tag ->
                TopicItem(
                    id = tag.id,
                    text = tag.title,
                )
            }
        },
    )
}