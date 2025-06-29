package `in`.tarun.gallery.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CircularLoaderTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCircularLoaderDisplays() {
        composeTestRule.setContent {
            CircularLoader()
        }

        composeTestRule.onNodeWithContentDescription("Loading indicator").assertExists()
    }

    @Test
    fun circularLoader_isDisplayed() {
        // When
        composeTestRule.setContent {
            CircularLoader()
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Loading indicator").assertIsDisplayed()
    }

    @Test
    fun circularLoader_withCustomModifier_isDisplayed() {
        // When
        composeTestRule.setContent {
            CircularLoader(containerModifier = androidx.compose.ui.Modifier)
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Loading indicator").assertIsDisplayed()
    }
} 