package `in`.tarun.gallery.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PermissionPromptCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPermissionPromptCardDisplaysDefaultText() {
        composeTestRule.setContent {
            PermissionPromptCard(
                onPermissionRequested = {}
            )
        }

        composeTestRule.onNodeWithText("Permission Required").assertExists()
        composeTestRule.onNodeWithText("Grant Permissions").assertExists()
    }

    @Test
    fun testPermissionPromptCardCallsOnPermissionRequested() {
        var permissionRequested = false
        
        composeTestRule.setContent {
            PermissionPromptCard(
                onPermissionRequested = { permissionRequested = true }
            )
        }

        composeTestRule.onNodeWithText("Grant Permissions").performClick()
        assert(permissionRequested)
    }
} 