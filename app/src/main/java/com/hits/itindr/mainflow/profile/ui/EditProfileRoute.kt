package com.hits.itindr.mainflow.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hits.core_ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditProfileRoute(
    navController: NavController,
    viewModel: EditProfileViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        viewModel.effect.collect { effect ->

            when(effect) {

                EditProfileEffect.Close -> {

                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(
                            "profile_updated",
                            true
                        )

                    navController.popBackStack()
                }
            }
        }
    }

    EditProfileScreen(
        state = state,
        onBack = {
            navController.popBackStack()
        },
        onSave = viewModel::onSaveClick,
        onChangeAvatarClick = {},
        onDeleteAvatarClick = {},
        onNameChange = viewModel::onNameChange,
        onAboutChange = viewModel::onAboutChange,
        onTopicsChanged = viewModel::onTopicsChanged,
        title = stringResource(R.string.additional_info),
    )
}