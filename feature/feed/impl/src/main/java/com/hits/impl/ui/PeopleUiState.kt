package com.hits.impl.ui

import com.hits.api.model.User

data class PeopleUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val snackbarMessage: String? = null,
    val closeProfileScreen: Boolean = false,
)