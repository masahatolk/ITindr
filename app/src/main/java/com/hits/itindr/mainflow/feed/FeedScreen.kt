package com.hits.itindr.mainflow.feed

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hits.core_ui.R
import com.hits.itindr.R.string.no_cards
import com.hits.itindr.StartActivity
import com.hits.itindr.mainflow.feed.swipeableCards.state.rememberSwipeableCardsState
import com.hits.itindr.mainflow.feed.swipeableCards.ui.SwipeableCardDirection
import com.hits.itindr.mainflow.feed.swipeableCards.ui.SwipeableCardsProperties
import com.hits.itindr.mainflow.feed.swipeableCards.ui.SwipeableProfileCard
import com.hits.itindr.mainflow.feed.swipeableCards.ui.lazy.LazySwipeableCards
import com.hits.itindr.mainflow.feed.swipeableCards.ui.lazy.items
import org.koin.androidx.compose.koinViewModel

private const val FEED_LOG_TAG = "FeedScreen"

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val profiles = state.profiles
    val swipeableCardsState = rememberSwipeableCardsState(itemCount = { profiles.size })
    val currentProfile = profiles.getOrNull(swipeableCardsState.currentCardIndex)
    val hasCardsToShow =
        currentProfile != null || swipeableCardsState.swipingVisibleCards.isNotEmpty()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onIntent(FeedIntent.LoadFeed)
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onIntent(FeedIntent.ErrorShown)
        }
    }
    LaunchedEffect(state.mutualMatchMessage) {
        state.mutualMatchMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onIntent(FeedIntent.MutualMatchShown)
        }
    }

    LaunchedEffect(state.isTokenExpired) {
        if (state.isTokenExpired) {
            context.startActivity(Intent(context, StartActivity::class.java))
            (context as? Activity)?.finish()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 84.dp)
            .systemBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                tint = Color.White,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .weight(1f),
                contentAlignment = Alignment.TopCenter,
            ) {
                when {
                    state.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }

                    !hasCardsToShow -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(no_cards),
                                color = Color.White,
                            )
                        }
                    }

                    else -> {
                        LazySwipeableCards(
                            modifier = Modifier
                                .fillMaxWidth(),
                            //.height(maxHeight),
                            state = swipeableCardsState,
                            properties = SwipeableCardsProperties(
                                padding = 0.dp,
                                stackedCardsOffset = 0.dp,
                            ),
                            onSwipe = { profile, direction ->
                                Log.d(FEED_LOG_TAG, "Swiped ${profile.name} to $direction")
                                when (direction) {
                                    SwipeableCardDirection.Right -> viewModel.onIntent(
                                        FeedIntent.Like(
                                            profile
                                        )
                                    )

                                    SwipeableCardDirection.Left -> viewModel.onIntent(
                                        FeedIntent.Dislike(
                                            profile
                                        )
                                    )
                                }
                            },
                        ) {
                            items(profiles) { profile, _, _ ->
                                SwipeableProfileCard(
                                    modifier = Modifier.fillMaxSize(),
                                    profile = profile,
                                    onLike = {
                                        if (profile == currentProfile) {
                                            swipeableCardsState.swipe(SwipeableCardDirection.Right)
                                            viewModel.onIntent(FeedIntent.Like(profile))
                                            Log.d(FEED_LOG_TAG, "Liked ${profile.name}")
                                        }
                                    },
                                    onDislike = {
                                        if (profile == currentProfile) {
                                            swipeableCardsState.swipe(SwipeableCardDirection.Left)
                                            viewModel.onIntent(FeedIntent.Dislike(profile))
                                            Log.d(FEED_LOG_TAG, "Disliked ${profile.name}")
                                        }
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}
