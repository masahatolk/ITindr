package com.hits.itindr.screen.providers

import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

abstract class ScreenStepProvider<out T : KScreen<T>>(
    val screen: T,
    val testContext: TestContext<*>,
) {

    fun step(stepName: String, block: T.() -> Unit) {
        testContext.step(stepName) {
            screen.block()
        }
    }
}