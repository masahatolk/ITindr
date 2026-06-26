package com.hits.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hits.api.model.User
import com.hits.core_ui.AppTextStyles
import com.hits.core_ui.R

@Composable
fun PeopleScreen(
    state: PeopleUiState,
    gridState: LazyGridState,
    onClick: (User) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),

        containerColor = Color.Transparent,

        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                    .systemBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.title_people),
                    color = Color.White,
                    style = AppTextStyles.Header
                )
            }
        }) { innerPadding ->

        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = (
                    PaddingValues(
                        start = 24.dp,
                        end = 24.dp,
                        top = innerPadding.calculateTopPadding() + 24.dp,
                        bottom = innerPadding.calculateBottomPadding() + 100.dp
                    )
                    ),

            ) {
            items(state.users) { user ->

                PeopleItemCard(
                    text = user.name,
                    avatar = user.avatar,
                    onClick = {
                        onClick(user)
                    },
                )
            }
        }
    }
}
