package com.hits.itindr.main_flow.feed

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hits.itindr.R
import com.hits.itindr.main_flow.feed.swipeable_cards.Profile
import com.hits.itindr.main_flow.feed.swipeable_cards.state.rememberSwipeableCardsState
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.SwipeableCardDirection
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.SwipeableCardsProperties
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.SwipeableProfileCard
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.lazy.LazySwipeableCards
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.lazy.items

private const val FEED_LOG_TAG = "FeedScreen"

private val mockProfiles = listOf(
    Profile(
        name = "Андрей Иванов",
        tags = listOf("Python", "Django", "REST"),
        description = "Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.Люблю программировать на питоне. Люблю изучать питон. Люблю всё, что угодно, связанное с питоном. А еще я люблю перл.",
        imageResName = "photo"
    ),
    Profile(
        name = "Мария Смирнова",
        tags = listOf("Kotlin", "Compose", "Android", "Git", "SQL", "UML"),
        description = "Собираю мобильные продукты, люблю чистую архитектуру и команды, где можно спорить о naming-е и всё равно остаться друзьями.",
        imageResName = "photo2"
    ),
    Profile(
        name = "Илья Петров",
        tags = listOf("Java", "Spring", "PostgreSQL"),
        description = "Пишу backend, автоматизирую рутину и радуюсь, когда фича едет в прод без ночных инцидентов. Всегда за хороший code review.",
        imageResName = "photo3"
    )
)


@Composable
fun FeedScreen() {
    val profiles = remember { mockProfiles }
    val swipeableCardsState = rememberSwipeableCardsState(itemCount = { profiles.size })
    val currentProfile = profiles.getOrNull(swipeableCardsState.currentCardIndex)


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



        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .weight(1f),
            contentAlignment = Alignment.TopCenter,
        ) {
            if (currentProfile == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Карточки закончились",
                        color = Color.White,
                    )
                }
            } else {
                LazySwipeableCards(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(maxHeight),
                    state = swipeableCardsState,
                    properties = SwipeableCardsProperties(
                        padding = 0.dp,
                        stackedCardsOffset = 0.dp,
                    ),
                    onSwipe = { profile, direction ->
                        Log.d(FEED_LOG_TAG, "Swiped ${profile.name} to $direction")
                    },
                ) {
                    items(profiles) { profile, _, _ ->
                        SwipeableProfileCard(
                            modifier = Modifier.fillMaxSize(),
                            profile = profile,
                            onLike = {
                                if (profile == currentProfile) {
                                    swipeableCardsState.swipe(SwipeableCardDirection.Right)
                                    Log.d(FEED_LOG_TAG, "Liked ${profile.name}")
                                }
                            },
                            onDislike = {
                                if (profile == currentProfile) {
                                    swipeableCardsState.swipe(SwipeableCardDirection.Left)
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

