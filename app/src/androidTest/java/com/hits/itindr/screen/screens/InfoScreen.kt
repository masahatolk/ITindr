package com.hits.itindr.screen.screens

import com.hits.itindr.InfoFragment
import com.hits.itindr.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton

object InfoScreen : KScreen<InfoScreen>() {

    override val layoutId = R.layout.fragment_info
    override val viewClass = InfoFragment::class.java

    val saveButton = KButton { withId(R.id.save_button) }
}