package com.hits.itindr.screen.tests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.hits.itindr.main_flow.MainActivity
import com.hits.itindr.screen.providers.MainScreenProvider.Companion.mainScreenProvider
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class MainFlowUiTest : TestCase() {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * 1. Отображение основных элементов
     */
    @Test
    fun mainElementsVisible() = run {
        step("Проверяем элементы на главном экране") {
            mainScreenProvider(composeTestRule) {
                checkMainElementsVisible()
            }
        }
    }

    /**
     * 2. Кнопка "Люди" переводит на экран Люди
     */
    @Test
    fun peopleButtonOpensPeopleScreen() = run {
        step("Нажимаем кнопку Люди") {
            mainScreenProvider(composeTestRule) {
                openPeopleScreen()
            }
        }

        step("Проверяем, что экран Люди открыт") {
            mainScreenProvider(composeTestRule) {
                checkPeopleScreenOpened()
            }
        }
    }

    /**
     * 3. Вертикальный скролл на главном экране затемняет фотографию
     */
    @Test
    fun verticalScrollDarkensPhoto() = run {
        step("Скроллим карточку вниз и проверяем затемнение") {
            mainScreenProvider(composeTestRule) {
                swipeCardAndCheckOverlay()
            }
        }
    }

    /**
     * 4. Нажатие на кнопку Dislike обновляет данные на экране
     */
    @Test
    fun dislikeUpdatesProfileData() = run {
        step("Нажимаем Dislike и проверяем обновление данных") {
            mainScreenProvider(composeTestRule) {
                dislikeAndCheckDataUpdated()
            }
        }
    }
}
