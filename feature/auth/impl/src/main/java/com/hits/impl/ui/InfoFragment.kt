package com.hits.impl.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.hits.core_media.camera.createTempImageUri
import com.hits.core_media.permission.galleryPermission
import com.hits.core_media.ui.PhotoPickerViewModel
import com.hits.core_ui.ActionButton
import com.hits.core_ui.applyStatusBarPadding
import com.hits.core_ui.photo.PhotoPickerBottomSheet
import com.hits.impl.R
import com.hits.impl.databinding.FragmentInfoBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : Fragment(R.layout.fragment_info) {
    private val viewModel: InfoViewModel by viewModel()
    private val photoPickerViewModel: PhotoPickerViewModel by viewModel()
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private val _selectedTopicIds = MutableStateFlow<Set<String>>(emptySet())
    private val selectedTopicIds = _selectedTopicIds.asStateFlow()

    private var selectedAvatarUri: Uri? = null

    private var isPhotoPickerVisible = false

    private lateinit var cameraUri: Uri

    private val takePhotoLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->

        if (success) {

            showAvatar(cameraUri)

            hidePicker()
        }
    }

    private val galleryPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->

        if (granted) {

            photoPickerViewModel.loadPhotos()

            showPicker()
        }
    }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->

        if (granted) {

            cameraUri = createTempImageUri(
                requireContext()
            )

            takePhotoLauncher.launch(
                cameraUri
            )
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentInfoBinding.bind(view)


        binding.addPhoto.setOnClickListener {

            photoPickerViewModel.clearSelection()

            galleryPermissionLauncher.launch(
                galleryPermission()
            )
        }

        binding.deletePhoto.setOnClickListener {

            selectedAvatarUri = null

            binding.avatar.scaleType = ImageView.ScaleType.CENTER_INSIDE
            binding.avatar.setImageResource(
                com.hits.core_ui.R.drawable.avatar
            )

            binding.deletePhoto.visibility = View.GONE
        }



        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.topics.collect { tags ->
                binding.tagView.tags = tags
            }
        }

        viewModel.loadTopics()

        binding.tagView.onSelectionChange = { selectedIds ->
            _selectedTopicIds.value = selectedIds.toSet()
        }

        binding.saveButton.setOnClickListener {

            val name = binding.nameInputLayout.editText?.text?.toString().orEmpty()

            val about = binding.additionalInfoInputLayout.editText?.text?.toString()

            viewModel.saveProfile(
                name = name,
                aboutMyself = about,
                topics = selectedTopicIds.value.toList(),
                onSuccess = {

                    val avatar = selectedAvatarUri

                    if (avatar == null) {

                        openMain()

                    } else {

                        viewModel.uploadAvatar(
                            avatar = avatar.toString(),

                            onSuccess = {
                                openMain()
                            },

                            onError = {
                                Snackbar.make(
                                    binding.root, "Не удалось загрузить фото", Snackbar.LENGTH_LONG
                                ).show()
                            })
                    }
                },
                onError = {
                    Snackbar.make(
                        binding.root, "Не удалось сохранить профиль", Snackbar.LENGTH_LONG
                    ).show()
                })
        }
    }

    private fun showPicker() {

        isPhotoPickerVisible = true

        binding.photoPickerCompose.visibility = View.VISIBLE

        binding.photoPickerCompose.setContent {

            val pickerState by photoPickerViewModel.state.collectAsState()

            PhotoPickerBottomSheet(
                state = pickerState,

                onPhotoClick = {

                    photoPickerViewModel.togglePhoto(
                        uri = it, multiSelect = false, maxSelection = 1
                    )
                },

                onCameraClick = {

                    cameraPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                },

                onDismiss = {

                    hidePicker()
                },

                currentElement = {

                    ActionButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Использовать фото",
                        enabled = pickerState.selectedPhotos.isNotEmpty(),
                        onClick = {

                            pickerState.selectedPhotos.firstOrNull()?.let {

                                showAvatar(it)

                                hidePicker()
                            }
                        })
                })
        }
    }


    private fun hidePicker() {

        isPhotoPickerVisible = false

        binding.photoPickerCompose.setContent {}

        binding.photoPickerCompose.visibility = View.GONE
    }

    private fun showAvatar(uri: Uri) {

        selectedAvatarUri = uri

        binding.avatar.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.avatar.load(uri)

        binding.deletePhoto.visibility = View.VISIBLE
    }

    private fun openMain() {
        startActivity(Intent(requireContext(), Class.forName("com.hits.itindr.mainflow.MainActivity")))
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}