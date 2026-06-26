package com.hits.impl.ui

sealed interface EditProfileEffect {

    data object Close : EditProfileEffect
}