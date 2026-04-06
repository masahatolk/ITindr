package com.hits.itindr.screen.providers

import com.hits.itindr.screen.screens.RegisterScreen
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

internal class RegisterScreenProvider(
    screen: RegisterScreen,
    testContext: TestContext<*>
) : ScreenStepProvider<RegisterScreen>(screen = screen, testContext = testContext) {

    fun clickRegister() {
        step("$TAG Нажимаем кнопку Register") {
            screen.registerButton.click()
        }
    }

    fun clickBack() {
        step("$TAG Нажимаем кнопку Back") {
            screen.backButton.click()
        }
    }

    companion object {
        private const val TAG = "RegisterScreenProvider"

        fun TestContext<*>.registerScreenProvider(
            block: RegisterScreenProvider.() -> Unit
        ) {
            RegisterScreenProvider(RegisterScreen, this).apply { block() }
        }
    }
}