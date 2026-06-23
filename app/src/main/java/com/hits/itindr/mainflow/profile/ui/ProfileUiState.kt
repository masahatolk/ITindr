package com.hits.itindr.mainflow.profile.ui

import com.hits.itindr.mainflow.profile.domain.Profile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val error: String? = null
)