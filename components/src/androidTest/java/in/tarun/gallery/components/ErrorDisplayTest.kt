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
class ErrorMessageCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testErrorMessageCardDisplaysErrorText() {
        val errorMessage = "Test error message"
        
        composeTestRule.setContent {
            ErrorMessageCard(errorText = errorMessage)
        }

        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }

    @Test
    fun testErrorMessageCardDisplaysCustomHeader() {
        val customHeader = "Custom Error Header"
        
        composeTestRule.setContent {
            ErrorMessageCard(
                errorText = "Error message",
                headerText = customHeader
            )
        }

        composeTestRule.onNodeWithText(customHeader).assertExists()
    }

    @Test
    fun testErrorMessageCardShowsRetryButtonWhenEnabled() {
        var retryClicked = false
        
        composeTestRule.setContent {
            ErrorMessageCard(
                errorText = "Error message",
                showRetryOption = true,
                onRetryPressed = { retryClicked = true }
            )
        }

        composeTestRule.onNodeWithText("Try Again").performClick()
        assert(retryClicked)
    }
} 