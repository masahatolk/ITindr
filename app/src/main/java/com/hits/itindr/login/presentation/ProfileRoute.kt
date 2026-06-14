package com.hits.itindr.login.presentation

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hits.itindr.AppGraph
import com.hits.itindr.StartActivity
import com.hits.itindr.mainflow.ProfileScreen

@Composable
fun ProfileRoute() {
    val viewModel: ProfileViewModel = viewModel(
        factory = AppGraph.provideProfileViewModelFactory()
    )

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                ProfileUiEvent.OpenLoginScreen -> {
                    context.startActivity(
                        Intent(context, StartActivity::class.java)
                    )

                    (context as? Activity)?.finish()
                }
            }
        }
    }

    ProfileScreen(
        onEditClick = {},
        onLogoutClick = {
            viewModel.logout()
        }
    )
}