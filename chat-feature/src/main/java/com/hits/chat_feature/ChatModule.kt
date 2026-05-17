package com.hits.chat_feature

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val chatModule = module {
    single { ChatMessageAppearanceDirector() }
    viewModel { ConversationViewModel(director = get()) }
}
