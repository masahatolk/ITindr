package com.hits.core_media.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.core_media.data.GalleryRepository
import com.hits.core_ui.photo.PhotoPickerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhotoPickerViewModel(
    private val repository: GalleryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        PhotoPickerUiState()
    )

    val state = _state.asStateFlow()

    fun loadPhotos() {

        if (_state.value.photos.isNotEmpty()) {
            return
        }

        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = true)
            }

            val photos = repository.loadPhotos()

            _state.update {
                it.copy(
                    isLoading = false, photos = photos
                )
            }
        }
    }

    fun reloadPhotos() {

        viewModelScope.launch {

            val photos =
                repository.loadPhotos()

            _state.update {
                it.copy(
                    photos = photos
                )
            }
        }
    }

    fun togglePhoto(
        uri: Uri, multiSelect: Boolean, maxSelection: Int
    ) {

        _state.update { state ->

            val selected = state.selectedPhotos

            if (uri in selected) {

                state.copy(
                    selectedPhotos = selected - uri
                )

            } else {

                if (!multiSelect) {

                    state.copy(
                        selectedPhotos = setOf(uri)
                    )

                } else {

                    if (selected.size >= maxSelection) {

                        state

                    } else {

                        state.copy(
                            selectedPhotos = selected + uri
                        )
                    }
                }
            }
        }
    }

    fun clearSelection() {

        _state.update {
            it.copy(selectedPhotos = emptySet())
        }
    }
}