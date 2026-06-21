package com.hits.itindr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.hits.core_ui.R

@Composable
fun GradientBackground(content: @Composable () -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()
        val maxDim = maxOf(width, height)

        Box {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.gray))
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colorStops = arrayOf(
                                0.0f to colorResource(R.color.purple).copy(alpha = 0.8f),
                                0.4f to colorResource(R.color.purple).copy(alpha = 0.3f),
                                0.7f to colorResource(R.color.purple).copy(alpha = 0.1f),
                                1.0f to Color.Transparent
                            ),
                            center = Offset(
                                x = width,
                                y = height * 0.0915f
                            ),
                            radius = maxDim * 0.9f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colorStops = arrayOf(
                                0.0f to colorResource(R.color.light_purple).copy(alpha = 0.8f),
                                0.5f to colorResource(R.color.light_purple).copy(alpha = 0.25f),
                                0.8f to colorResource(R.color.light_purple).copy(alpha = 0.05f),
                                1.0f to Color.Transparent
                            ),
                            center = Offset(
                                x = width * -0.0445f,
                                y = height * 0.409f
                            ),
                            radius = maxDim * 0.5f
                        )
                    )
            )

            content()
        }
    }
}
