package com.hits.itindr.mainflow.feed

import com.hits.itindr.mainflow.profile.domain.Profile


data class PeopleUiState(
    val users: List<Profile> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val snackbarMessage: String? = null,
    val closeProfileScreen: Boolean = false,
)