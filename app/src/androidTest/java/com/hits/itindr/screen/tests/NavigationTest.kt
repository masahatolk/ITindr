package com.hits.itindr.screen.tests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.hits.itindr.mainflow.MainActivity
import com.hits.itindr.StartActivity
import com.hits.itindr.screen.providers.HomeScreenProvider.Companion.homeScreenProvider
import com.hits.itindr.screen.providers.InfoScreenProvider.Companion.infoScreenProvider
import com.hits.itindr.screen.providers.MainScreenProvider.Companion.mainScreenProvider
import com.hits.itindr.screen.providers.RegisterScreenProvider.Companion.registerScreenProvider
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class NavigationTest : TestCase() {

    @get:Rule
    val activityRule = ActivityScenarioRule(StartActivity::class.java)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * 1. Отображение основных элементов
     */
    @Test
    fun homeElementsVisible() = run {

        step("Ждем окончания анимации") {
            homeScreenProvider {
                waitAnimationsEnd()
            }
        }

        step("Проверяем видимость и активность Home экрана") {

            homeScreenProvider {
                checkHomeScreenVisible()
            }
        }
    }

    /**
     * 2. Кнопка «Назад» возвращает на Home
     */
    @Test
    fun backButtonReturnsHome() = run {
        step("Ждем пока кнопка станет активной") {
            homeScreenProvider {
                waitAnimationsEnd()
            }
        }

        step("Home -> Register") {
            homeScreenProvider {
                openRegisterScreen()
            }
        }

        step("Нажимаем Back на экране Register") {
            registerScreenProvider {
                clickBack()
            }
        }

        step("Снова видим Home") {
            homeScreenProvider {
                checkHomeScreenVisible()
            }
        }
    }

    /**
     * 3. Системный Back возвращает на Home
     */
    @Test
    fun systemBackReturnsHome() = run {

        step("Ждем пока кнопка станет активной") {
            homeScreenProvider {
                waitAnimationsEnd()
            }
        }

        step("Home -> Register") {
            homeScreenProvider {
                openRegisterScreen()
            }
        }

        step("System Back") {
            device.uiDevice.pressBack()
        }

        step("Снова видим Home") {
            homeScreenProvider {
                checkHomeScreenVisible()
            }
        }
    }

    /**
     * 4. Навигация на главный экран
     */
    @Test
    fun registrationFlowOpensMain() = run {

        step("Home -> Register") {
            homeScreenProvider {
                openRegisterScreen()
            }
        }

        step("Register -> Register button") {
            registerScreenProvider {
                clickRegister()
            }
        }

        step("Info -> Save") {
            infoScreenProvider {
                clickSave()
            }
        }

        step("Проверяем главный экран") {
            mainScreenProvider(composeTestRule) {
                checkMainVisible()
            }
        }
    }
}