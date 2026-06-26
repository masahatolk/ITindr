package com.hits.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hits.api.model.User
import com.hits.core_ui.Toolbar
import com.hits.core_ui.UserCard
import com.hits.impl.ui.swipeableCards.ui.ReactionPanel

@Composable
fun PeopleUserCard(
    profile: User,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onBack: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),

            containerColor = Color.Transparent,

            topBar = {
                Toolbar(
                    title = "",
                    onBack = onBack
                )
            },

            bottomBar = {
                Box(
                    modifier = Modifier.padding(24.dp)
                ) {
                    ReactionPanel(onLike, onDislike)
                }
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) { innerPadding ->

            UserCard(
                name = profile.name,
                about = profile.about,
                avatar = profile.avatar,
                topics = profile.topics,
                modifier = Modifier
                    .padding(
                        PaddingValues(
                            start = 24.dp,
                            end = 24.dp,
                            top = innerPadding.calculateTopPadding() + 16.dp,
                            bottom = innerPadding.calculateBottomPadding() + 100.dp
                        )
                    ),
            )
        }
    }
}