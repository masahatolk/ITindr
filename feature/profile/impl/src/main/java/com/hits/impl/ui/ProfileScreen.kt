package com.hits.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hits.core_ui.CircleIconButton
import com.hits.core_ui.FullScreenLoader
import com.hits.core_ui.UserCard
import com.hits.core_ui.R.drawable.edit
import com.hits.core_ui.R.drawable.logout
import com.hits.core_ui.R

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onEditClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        containerColor = Color.Transparent,

        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                    .systemBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.title_profile),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                CircleIconButton(
                    icon = edit,
                    onClick = onEditClick
                )

                Spacer(modifier = Modifier.width(16.dp))

                CircleIconButton(
                    icon = logout,
                    onClick = onLogoutClick
                )
            }
        },

        ) { innerPadding ->

        when {

            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            state.profile != null -> {

                UserCard(
                    name = state.profile.name,
                    about = state.profile.about,
                    avatar = state.profile.avatar,
                    topics = state.profile.topics,
                    modifier = Modifier
                        .padding(
                            PaddingValues(
                                start = 24.dp,
                                end = 24.dp,
                                top = innerPadding.calculateTopPadding() + 24.dp,
                                bottom = innerPadding.calculateBottomPadding() + 100.dp
                            )
                        ),
                )
            }
        }
    }
}

