package com.hits.itindr.screen

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun hasStatusBarPadding(): Matcher<View> {
    return object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
            description.appendText("View has status bar padding")
        }

        override fun matchesSafely(view: View): Boolean {

            val insets = ViewCompat.getRootWindowInsets(view)
            val statusBarHeight =
                insets?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: return false

            return view.paddingTop >= statusBarHeight
        }
    }
}