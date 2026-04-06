package com.hits.itindr.main_flow

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hits.itindr.GradientBackground

private const val SCREEN_TRANSITION_DURATION_MS = 320

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val stateHolder = rememberSaveableStateHolder()

    GradientBackground {
        Column(
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues()),
        ) {
            AnimatedContent(
                targetState = viewModel.selectedIndex,
                modifier = Modifier.weight(1f),
                transitionSpec = {
                    val direction = if (targetState > initialState) 1 else -1

                    slideIntoContainer(
                        towards = if (direction > 0) {
                            AnimatedContentTransitionScope.SlideDirection.Left
                        } else {
                            AnimatedContentTransitionScope.SlideDirection.Right
                        },
                        animationSpec = tween(
                            durationMillis = SCREEN_TRANSITION_DURATION_MS,
                            easing = FastOutSlowInEasing,
                        ),
                    ).togetherWith(
                        slideOutOfContainer(
                            towards = if (direction > 0) {
                                AnimatedContentTransitionScope.SlideDirection.Left
                            } else {
                                AnimatedContentTransitionScope.SlideDirection.Right
                            },
                            animationSpec = tween(
                                durationMillis = SCREEN_TRANSITION_DURATION_MS,
                                easing = FastOutSlowInEasing,
                            ),
                        )
                    ).using(
                        SizeTransform(clip = true),
                    )
                },
                label = "main_screen_navigation",
            ) { selectedIndex ->
                val screen = screens[selectedIndex]

                stateHolder.SaveableStateProvider(screen.route) {
                    screen.content()
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BottomNavigation(
                    selectedIndex = viewModel.selectedIndex,
                    onItemSelected = { index ->
                        viewModel.selectedIndex = index
                    }
                )
            }
        }
    }
}