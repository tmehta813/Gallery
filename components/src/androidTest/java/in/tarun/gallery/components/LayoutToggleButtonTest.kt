package `in`.tarun.gallery.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewModeToggleTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testViewModeToggleDisplays() {
        composeTestRule.setContent {
            ViewModeToggle(
                currentViewMode = DisplayMode.Grid,
                onViewModeChanged = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Switch view mode").assertExists()
    }

    @Test
    fun testViewModeToggleChangesMode() {
        var currentMode = DisplayMode.Grid
        
        composeTestRule.setContent {
            ViewModeToggle(
                currentViewMode = currentMode,
                onViewModeChanged = { currentMode = it }
            )
        }

        composeTestRule.onNodeWithContentDescription("Switch view mode").performClick()
        assert(currentMode == DisplayMode.List)
    }
} 