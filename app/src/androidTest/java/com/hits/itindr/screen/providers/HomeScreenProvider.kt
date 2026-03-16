package com.hits.itindr.screen.providers

import com.hits.itindr.screen.hasStatusBarPadding
import com.hits.itindr.screen.screens.HomeScreen
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

internal class HomeScreenProvider (
    screen: HomeScreen,
    testContext: TestContext<*>
) : ScreenStepProvider<HomeScreen>(screen = screen, testContext = testContext) {

    fun waitAnimationsEnd() {
        step("$TAG Ожидание окончания анимаций") {
            screen.registerButton.isEnabled()
        }
    }

    fun openRegisterScreen() {
        step("$TAG Нажатие на кнопку регистрации") {
            screen.registerButton.click()
        }
    }

    fun checkAllElementsVisible() {
        step("$TAG Проверка видимости элементов") {
            screen.apply {
                backgroundImage.isVisible()
                objectsImage.isVisible()
                logoBase.isVisible()
                logoDot.isVisible()
                logoText.isVisible()
                registerButton.isVisible()
                loginButton.isVisible()
                logoBase {
                    matches { hasStatusBarPadding() }
                }
            }

        }
    }

    fun checkButtonsEnabled() {
        step("$TAG Проверка активности кнопок") {
            screen.apply {
                registerButton.isEnabled()
                loginButton.isEnabled()
            }
        }
    }

    fun checkHomeScreenVisible() {
        step("$TAG Проверка видимости и активности Home экрана") {
            checkAllElementsVisible()
            checkButtonsEnabled()
        }
    }

    companion object {
        private const val TAG: String = "HomeScreenProvider"

        fun TestContext<*>.homeScreenProvider(
            block: HomeScreenProvider.() -> Unit
        ) {
            HomeScreenProvider(HomeScreen, this).apply { block() }
        }
    }
}