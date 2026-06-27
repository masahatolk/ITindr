package com.hits.impl.ui

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileRoute(
    navController: NavController,
) {
    val viewModel: ProfileViewModel = koinViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val savedStateHandle =
        navController.currentBackStackEntry
            ?.savedStateHandle

    val context = LocalContext.current

    LaunchedEffect(savedStateHandle) {

        savedStateHandle
            ?.getStateFlow(
                "profile_updated",
                false
            )
            ?.collect { updated ->

                if (updated) {

                    viewModel.loadProfile()

                    savedStateHandle["profile_updated"] = false
                }
            }
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                ProfileUiEvent.OpenLoginScreen -> {
                    context.startActivity(
                        Intent(context, Class.forName("com.hits.itindr.StartActivity"))
                    )

                    (context as? Activity)?.finish()
                }
            }
        }
    }

    ProfileScreen(
        state = state,
        onEditClick = {
            navController.navigate("edit_profile")
        },
        onLogoutClick = {
            viewModel.logout()
        },
    )
}