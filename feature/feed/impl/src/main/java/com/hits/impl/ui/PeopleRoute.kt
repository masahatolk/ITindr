package com.hits.impl.ui

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hits.itindr.mainflow.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun PeopleRoute (
    navController: NavController,
) {
    val viewModel: PeopleViewModel = koinViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val gridState = rememberLazyGridState()

    LaunchedEffect(gridState) {

        snapshotFlow {

            gridState.layoutInfo

        }.collect { layoutInfo ->

            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            val total = layoutInfo.totalItemsCount

            if (lastVisible >= total - 4) {
                viewModel.loadNextPage()
            }
        }
    }

    PeopleScreen(
        state = state,
        gridState = gridState,
        onClick = { user ->

            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("profile", user)

            navController.navigate(
                Screen.PeopleProfile.route
            )
        }
    )
}