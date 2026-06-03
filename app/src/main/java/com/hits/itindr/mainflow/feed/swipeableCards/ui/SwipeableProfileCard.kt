package com.hits.itindr.mainflow.feed.swipeableCards.ui

import android.view.ContextThemeWrapper
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.hits.itindr.R
import com.hits.itindr.TagFlowView
import com.hits.itindr.TagItem
import com.hits.itindr.mainflow.feed.swipeableCards.Profile
import kotlin.math.roundToInt

private const val CARD_CORNER_RADIUS = 32
private const val DETAILS_DRAG_RANGE = 240f
private const val DESCRIPTION_REVEAL_OFFSET = 56f
private const val COLLAPSED_CONTENT_OFFSET = 92f
private const val SCROLL_INDICATOR_TRAVEL = 162f

@Composable
fun SwipeableProfileCard(
    modifier: Modifier = Modifier,
    profile: Profile,
    onLike: () -> Unit,
    onDislike: () -> Unit,
) {
    var detailsProgress by remember { mutableFloatStateOf(0f) }

    val animatedProgress by animateFloatAsState(
        targetValue = detailsProgress,
        animationSpec = tween(durationMillis = 220),
        label = "details_progress",
    )

    val imageResId = rememberDrawableId(profile.imageResName)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(CARD_CORNER_RADIUS.dp))
            .background(colorResource(R.color.gray)),
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = profile.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = animatedProgress * 0.5f))
        )

        DescriptionScrollIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onVerticalDrag = { change, dragAmount ->
                            change.consume()
                            val nextProgress = (detailsProgress - dragAmount / DETAILS_DRAG_RANGE)
                                .coerceIn(0f, 1f)
                            detailsProgress = nextProgress
                        },
                        onDragEnd = {
                            detailsProgress = if (detailsProgress >= 0.45f) 1f else 0f
                        },
                    )
                }
                .padding(horizontal = 18.dp, vertical = 20.dp),
        ) {
            Spacer(
                modifier = Modifier.height(
                    (COLLAPSED_CONTENT_OFFSET * (1f - animatedProgress)).roundToInt().dp,
                ),
            )

            Text(
                text = profile.name,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProfileTagsFlow(
                tags = profile.tags,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )

            val descriptionOffset = ((1f - animatedProgress) * DESCRIPTION_REVEAL_OFFSET).roundToInt()
            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height((132f * animatedProgress).dp)
                    .graphicsLayer {
                        alpha = animatedProgress
                    },
            ) {
                Text(
                    text = profile.description,
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 19.sp,
                    modifier = Modifier.offset(y = descriptionOffset.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                CardActionButton(
                    color = colorResource(R.color.red),
                    icon = painterResource(id = R.drawable.close),
                    onClick = onDislike,
                    modifier = Modifier.weight(1f),
                )

                CardActionButton(
                    color = colorResource(R.color.green),
                    icon = painterResource(id = R.drawable.like),
                    onClick = onLike,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun rememberDrawableId(drawableName: String): Int {
    val context = LocalContext.current
    return remember(drawableName, context) {
        val resolvedId = context.resources.getIdentifier(
            drawableName,
            "drawable",
            context.packageName,
        )
        if (resolvedId != 0) resolvedId else R.drawable.logo
    }
}

@Composable
private fun DescriptionScrollIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(210.dp)
            .width(6.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(Color.Black.copy(alpha = 0.45f)),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (SCROLL_INDICATOR_TRAVEL * progress).roundToInt().dp)
                .width(6.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(Color.White),
        )
    }
}

@Composable
private fun ProfileTagsFlow(
    tags: List<String>,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TagFlowView(ContextThemeWrapper(context, R.style.SwipeableProfileTagFlowView)).apply {
                setOnTouchListener { _, _ -> true }
                isClickable = false
                isFocusable = false
            }
        },
        update = { view ->
            view.tags = tags.mapIndexed { index, tag ->
                TagItem(id = index, text = tag)
            }
        },
    )
}

@Composable
private fun CardActionButton(
    color: Color,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .background(color)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.widthIn(min = 32.dp),
        )
    }
}
