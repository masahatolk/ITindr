package com.hits.core_media.di

import com.hits.core_media.data.GalleryRepository
import com.hits.core_media.data.GalleryRepositoryImpl
import com.hits.core_media.ui.PhotoPickerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mediaModule = module {

    single<GalleryRepository> {
        GalleryRepositoryImpl(get())
    }

    viewModel {
        PhotoPickerViewModel(get())
    }
}