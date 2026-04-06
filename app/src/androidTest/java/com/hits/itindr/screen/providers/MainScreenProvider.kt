package com.hits.itindr.screen.providers

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.hits.itindr.screen.screens.MainScreen
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

class MainScreenProvider(
    private val composeRule: ComposeTestRule,
    private val testContext: TestContext<*>
) {

    fun checkMainVisible() {
        testContext.step("$TAG Проверяем главный экран") {
            MainScreen.isVisible(composeRule)
        }
    }

    companion object {
        private const val TAG = "MainScreenProvider"

        fun TestContext<*>.mainScreenProvider(
            composeRule: ComposeTestRule,
            block: MainScreenProvider.() -> Unit
        ) {
            MainScreenProvider(composeRule, this).apply { block() }
        }
    }
}