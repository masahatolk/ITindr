package com.hits.itindr.screen.screens

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import androidx.compose.ui.test.swipeUp
import com.hits.itindr.main_flow.NAV_ITEM_PREFIX_TAG
import com.hits.itindr.main_flow.PEOPLE_SCREEN_TITLE_TAG
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.PROFILE_CARD_TAG
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.PROFILE_DETAILS_DRAG_AREA_TAG
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.PROFILE_DISLIKE_BUTTON_TAG
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.PROFILE_INTERESTS_TAG
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.PROFILE_LIKE_BUTTON_TAG
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.PROFILE_OVERLAY_TAG
import com.hits.itindr.main_flow.feed.swipeable_cards.ui.PROFILE_PHOTO_TAG
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotEquals

object MainScreen {

    fun isVisible(rule: ComposeTestRule) {
        rule.onAllNodesWithTag(PROFILE_CARD_TAG).onFirst().assertIsDisplayed()
    }

    fun checkMainElements(rule: ComposeTestRule) {
        rule.onAllNodesWithTag(PROFILE_PHOTO_TAG).onFirst().assertIsDisplayed()
        rule.onNodeWithText("Андрей Иванов").assertIsDisplayed()
        rule.onAllNodesWithTag(PROFILE_INTERESTS_TAG).onFirst().assertIsDisplayed()
        rule.onAllNodesWithTag(PROFILE_DISLIKE_BUTTON_TAG).onFirst().assertIsDisplayed()
        rule.onAllNodesWithTag(PROFILE_LIKE_BUTTON_TAG).onFirst().assertIsDisplayed()
        rule.onAllNodesWithTag("${NAV_ITEM_PREFIX_TAG}Поток").onFirst().assertIsSelected()
        rule.onAllNodesWithTag("${NAV_ITEM_PREFIX_TAG}Люди").onFirst().assertIsDisplayed()
    }

    fun openPeopleScreen(rule: ComposeTestRule) {
        rule.onAllNodesWithTag("${NAV_ITEM_PREFIX_TAG}Люди").onFirst().performClick()
    }

    fun checkPeopleScreenOpened(rule: ComposeTestRule) {
        val peopleTabTag = "${NAV_ITEM_PREFIX_TAG}Люди"

        rule.waitUntil(timeoutMillis = 5_000) {
            rule.onAllNodesWithTag(peopleTabTag).fetchSemanticsNodes().isNotEmpty()
        }

        rule.onAllNodesWithTag(peopleTabTag).onFirst().assertIsSelected()
        rule.onAllNodesWithTag(PEOPLE_SCREEN_TITLE_TAG).onFirst().assertIsDisplayed()
    }

    fun swipeCardAndCheckOverlay(rule: ComposeTestRule) {
        val overlayBefore = getMaxOverlayAlpha(rule)
        var overlayAfter = overlayBefore

        for (attempt in 1..3) {
            rule.onAllNodesWithTag(PROFILE_DETAILS_DRAG_AREA_TAG).onFirst().performTouchInput {
                swipe(
                    start = Offset(x = centerX, y = bottom - 1f),
                    end = Offset(x = centerX, y = top + 1f),
                    durationMillis = 300,
                )
            }
            rule.waitForIdle()
            overlayAfter = getMaxOverlayAlpha(rule)

            if (overlayAfter > 0) {
                break
            }
        }

        rule.waitUntil(timeoutMillis = 5_000) {
            getMaxOverlayAlpha(rule) > 0
        }

        assertEquals(0, overlayBefore)
        assertNotEquals(0, overlayAfter)
    }

    fun dislikeAndCheckDataUpdated(rule: ComposeTestRule) {
        rule.onNodeWithText("Андрей Иванов").assertIsDisplayed()
        rule.onAllNodesWithTag(PROFILE_DISLIKE_BUTTON_TAG).onFirst().performClick()

        rule.waitUntil(timeoutMillis = 5_000) {
            rule.onAllNodesWithText("Мария Смирнова").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithText("Мария Смирнова").assertIsDisplayed()
    }

    private fun getMaxOverlayAlpha(rule: ComposeTestRule): Int {
        return rule.onAllNodesWithTag(PROFILE_OVERLAY_TAG).fetchSemanticsNodes()
            .mapNotNull { node ->
                node.config.getOrNull(SemanticsProperties.StateDescription)
                    ?.removePrefix("overlay_alpha_")
                    ?.toIntOrNull()
            }
            .maxOrNull() ?: 0
    }
}