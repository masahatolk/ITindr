package com.hits.impl.ui

import com.hits.api.model.Profile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val error: String? = null
)