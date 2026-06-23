package com.hits.itindr.mainflow.profile.ui

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hits.core_media.camera.createTempImageUri
import com.hits.core_media.permission.galleryPermission
import com.hits.core_media.ui.PhotoPickerViewModel
import com.hits.core_ui.R
import com.hits.core_ui.photo.PhotoPickerBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditProfileRoute(
    navController: NavController,
    viewModel: EditProfileViewModel = koinViewModel()
) {

    val pickerViewModel = koinViewModel<PhotoPickerViewModel>()
    val pickerState by pickerViewModel.state.collectAsStateWithLifecycle()

    val state by viewModel.state.collectAsStateWithLifecycle()

    var showPicker by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val cameraUri = remember {
        createTempImageUri(context)
    }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->

        if (success) {

            showPicker = false

            viewModel.onAvatarSelected(
                cameraUri
            )
        }
    }

    val galleryPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            Log.d("PHOTO_PICKER", "gallery permission = $granted")

            if (granted) {

                pickerViewModel.loadPhotos()

                showPicker = true
            }
        }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->

        if (granted) {

            takePhotoLauncher.launch(
                cameraUri
            )
        }
    }

    LaunchedEffect(Unit) {

        viewModel.effect.collect { effect ->

            when (effect) {

                EditProfileEffect.Close -> {

                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "profile_updated", true
                    )

                    navController.popBackStack()
                }
            }
        }
    }

    if (showPicker) {

        PhotoPickerBottomSheet(
            state = pickerState,

            onPhotoClick = {
                pickerViewModel.selectPhoto(it)
            },

            onCameraClick = {
                cameraPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            },

            onUsePhotoClick = {

                pickerState.selectedPhoto?.let {

                    viewModel.onAvatarSelected(it)

                    showPicker = false
                }
            },

            onDismiss = {
                showPicker = false
            }
        )
    }

    EditProfileScreen(
        state = state,
        onBack = {
            navController.popBackStack()
        },
        onSave = viewModel::onSaveClick,
        onChangeAvatarClick = {
            Log.d("PHOTO_PICKER", "chip clicked")

            pickerViewModel.clearSelection()

            galleryPermissionLauncher.launch(
                galleryPermission()
            )

        },
        onDeleteAvatarClick = viewModel::onDeleteAvatarClick,
        onNameChange = viewModel::onNameChange,
        onAboutChange = viewModel::onAboutChange,
        onTopicsChanged = viewModel::onTopicsChanged,
        title = stringResource(R.string.additional_info),
    )
}