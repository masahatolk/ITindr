package com.hits.itindr.mainflow.match

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hits.itindr.GradientBackground
import com.hits.itindr.mainflow.match.avatars.MatchAvatars
import com.hits.itindr.mainflow.match.button.MatchMessageButton
import com.hits.itindr.mainflow.match.title.MatchTitle
import com.hits.itindr.mainflow.match.title.SmallTitle
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MatchOverlay(
    matchData: MatchData,
    onMessageClick: () -> Unit,
    onDismiss: () -> Unit,
) {

    val backgroundAlpha = remember {
        Animatable(0f)
    }

    var visibleLetters by remember {
        mutableIntStateOf(0)
    }

    val borderAlpha = remember {
        Animatable(0f)
    }

    val leftOffset = remember {
        Animatable(-500f)
    }

    val rightOffset = remember {
        Animatable(500f)
    }

    val lineProgress = remember {
        Animatable(0f)
    }

    val glowAlpha = remember {
        Animatable(0f)
    }

    val smallTitleAlpha = remember {
        Animatable(0f)
    }

    val buttonAlpha = remember {
        Animatable(0f)
    }


    LaunchedEffect(Unit) {

        backgroundAlpha.animateTo(
            1f,
            tween(500)
        )



        coroutineScope {

            launch {
                repeat(13) {
                    delay(120)
                    visibleLetters++

                    Log.d(
                        "MATCH",
                        "visibleLetters = $visibleLetters"
                    )
                }
            }

            launch {
                leftOffset.animateTo(
                    0f, tween(700)
                )
            }

            launch {
                rightOffset.animateTo(
                    0f, tween(700)
                )

                borderAlpha.animateTo(
                    1f, tween(400)
                )

                lineProgress.animateTo(
                    1f, tween(500)
                )
            }
        }

        coroutineScope {
            launch {
                glowAlpha.animateTo(
                    1f, tween(800)
                )
            }

            launch {
                buttonAlpha.animateTo(
                    1f, tween(300)
                )
            }

            launch {
                smallTitleAlpha.animateTo(
                    1f, tween(500)
                )
            }
        }
    }

    GradientBackground (
        alpha = backgroundAlpha.value
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    contentAlignment = Alignment.Center
                ){

                    MatchTitle(
                        visibleLetters = visibleLetters,
                        glowAlpha = glowAlpha.value
                    )
                }

                Spacer(
                    Modifier.height(32.dp)
                )

                MatchAvatars(
                    currentUserAvatar = matchData.currentUserAvatar,
                    matchedAvatar = matchData.matchedUser.avatar,
                    leftOffset = leftOffset.value,
                    rightOffset = rightOffset.value,
                    borderAlpha = borderAlpha.value,
                    lineProgress = lineProgress.value
                )

                Spacer(
                    Modifier.height(24.dp)
                )

                SmallTitle(
                    alpha = smallTitleAlpha.value
                )
            }

            MatchMessageButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                visible = buttonAlpha.value > 0.9f,
                onClick = onMessageClick,
            )

            IconButton(
                modifier = Modifier.align(
                    Alignment.TopEnd
                ),
                onClick = onDismiss,
                enabled = true,
                content = {},
            )
        }
    }
}