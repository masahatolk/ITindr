package com.hits.itindr.mainflow.profile.ui

sealed interface EditProfileEffect {

    data object Close : EditProfileEffect
}