package com.hits.itindr.main_flow.feed

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hits.itindr.R
import kotlin.math.roundToInt

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    name: String,
    tags: List<String>,
    description: String,
    imageUrl: String,
    onLike: () -> Unit,
    onDislike: () -> Unit
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    var offsetX by remember { mutableFloatStateOf(0f) }

    val swipeThreshold = 300f

    val darkAlpha by animateFloatAsState(
        targetValue = (scrollState.value / 600f).coerceIn(0f, 0.7f)
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .background(Color.Black)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        when {
                            offsetX > swipeThreshold -> onLike()
                            offsetX < -swipeThreshold -> onDislike()
                        }
                        offsetX = 0f
                    },
                    onDrag = { change, dragAmount ->
                        offsetX += dragAmount.x
                    }
                )
            }
            .offset { IntOffset(offsetX.roundToInt(), 0) }
    ) {

        /*AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )*/

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = darkAlpha))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                name, color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Row {
                tags.forEach {
                    TagChip(it)
                    Spacer(Modifier.width(8.dp))
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = description,
                color = Color.White,
                fontSize = 14.sp,
                maxLines = Int.MAX_VALUE,
                overflow = TextOverflow.Clip
            )

            Spacer(Modifier.height(80.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ActionButton(
                color = colorResource(R.color.red),
                icon = painterResource(id = R.drawable.close),
                onClick = onDislike,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(16.dp))

            ActionButton(
                color = colorResource(R.color.green),
                icon = painterResource(id = R.drawable.like),
                onClick = onLike,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TagChip(text: String) {
    Box(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun ActionButton(
    color: Color,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .background(color)
            .padding(16.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = Color.White)
    }
}