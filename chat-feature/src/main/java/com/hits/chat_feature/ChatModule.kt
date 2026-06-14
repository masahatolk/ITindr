package com.hits.chat_feature

import org.koin.dsl.module

val chatModule = module {
    single { ChatMessageAppearanceDirector() }
}
