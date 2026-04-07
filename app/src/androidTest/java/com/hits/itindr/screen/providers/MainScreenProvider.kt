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

    fun checkMainElementsVisible() {
        testContext.step("$TAG Проверяем основные элементы главного экрана") {
            MainScreen.checkMainElements(composeRule)
        }
    }

    fun openPeopleScreen() {
        testContext.step("$TAG Нажимаем кнопку Люди") {
            MainScreen.openPeopleScreen(composeRule)
        }
    }

    fun checkPeopleScreenOpened() {
        testContext.step("$TAG Проверяем, что открыт экран Люди") {
            MainScreen.checkPeopleScreenOpened(composeRule)
        }
    }

    fun swipeCardAndCheckOverlay() {
        testContext.step("$TAG Скроллим карточку и проверяем затемнение") {
            MainScreen.swipeCardAndCheckOverlay(composeRule)
        }
    }

    fun dislikeAndCheckDataUpdated() {
        testContext.step("$TAG Нажимаем Dislike и проверяем обновление данных") {
            MainScreen.dislikeAndCheckDataUpdated(composeRule)
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