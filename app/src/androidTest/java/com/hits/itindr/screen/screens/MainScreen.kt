package com.hits.itindr.screen.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag

object MainScreen {

    fun isVisible(rule: ComposeTestRule) {
        rule.onNodeWithTag("main_screen").assertIsDisplayed()
    }

}