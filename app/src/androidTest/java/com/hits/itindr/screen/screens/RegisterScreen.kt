package com.hits.itindr.screen.screens

import com.hits.itindr.R
import com.hits.itindr.RegisterFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object RegisterScreen : KScreen<RegisterScreen>() {

    override val layoutId = R.layout.fragment_register
    override val viewClass = RegisterFragment::class.java

    val header = KTextView { withId(R.id.register_header) }

    val registerButton = KButton { withId(R.id.register_button) }
    val backButton = KButton { withId(R.id.back_register_button) }
}