package com.hits.itindr.mainflow.feed

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.hits.itindr.mainflow.profile.domain.Profile
import org.koin.androidx.compose.koinViewModel

@Composable
fun PeopleProfileRoute(
    navController: NavController
) {
    val viewModel: PeopleViewModel = koinViewModel()

    val state by viewModel.state.collectAsState()

    val profile =
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<Profile>("profile")
            ?: return

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(state.closeProfileScreen) {

        if (state.closeProfileScreen) {

            navController.popBackStack()

            viewModel.profileScreenClosed()
        }
    }

    LaunchedEffect(state.snackbarMessage) {

        state.snackbarMessage?.let { message ->

            snackbarHostState.showSnackbar(message)

            viewModel.snackbarShown()
        }
    }

    PeopleUserCard(
        profile = profile,
        onBack = {
            navController.popBackStack()
        },
        onLike = {
            viewModel.likeProfile(profile.id)
        },
        onDislike = {
            viewModel.dislikeProfile(profile.id)
        },
        snackbarHostState = snackbarHostState,
    )
}