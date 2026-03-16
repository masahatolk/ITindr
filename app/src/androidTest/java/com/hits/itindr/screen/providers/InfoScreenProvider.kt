package com.hits.itindr.screen.providers

import com.hits.itindr.screen.screens.InfoScreen
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

internal class InfoScreenProvider(
    screen: InfoScreen,
    testContext: TestContext<*>
) : ScreenStepProvider<InfoScreen>(screen = screen, testContext = testContext) {

    fun clickSave() {
        step("$TAG Нажимаем кнопку Save") {
            screen.saveButton.click()
        }
    }

    companion object {
        private const val TAG = "InfoScreenProvider"

        fun TestContext<*>.infoScreenProvider(
            block: InfoScreenProvider.() -> Unit
        ) {
            InfoScreenProvider(InfoScreen, this).apply { block() }
        }
    }
}