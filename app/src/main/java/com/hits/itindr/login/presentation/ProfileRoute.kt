package com.hits.itindr.login.presentation

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.hits.itindr.StartActivity
import com.hits.itindr.mainflow.ProfileScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileRoute() {
    val viewModel: ProfileViewModel = koinViewModel()

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