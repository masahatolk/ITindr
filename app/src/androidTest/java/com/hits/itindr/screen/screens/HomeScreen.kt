package com.hits.itindr.screen.screens

import com.hits.itindr.HomeFragment
import com.hits.itindr.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object HomeScreen : KScreen<HomeScreen>() {

    override val layoutId = R.layout.fragment_home
    override val viewClass = HomeFragment::class.java

    val backgroundImage = KImageView { withId(R.id.background_image) }
    val objectsImage = KImageView { withId(R.id.objects_image) }

    val logoBase = KImageView { withId(R.id.logo_base) }
    val logoDot = KImageView { withId(R.id.logo_dot) }

    val logoText = KTextView { withId(R.id.logo_text) }
    val registerButton = KButton { withId(R.id.register_home_button) }
    val loginButton = KButton { withId(R.id.login_home_button) }
}