package `in`.tarun.gallery.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GridLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testGridLayoutDisplays() {
        val testData = listOf("Item 1", "Item 2", "Item 3")
        
        composeTestRule.setContent {
            GridLayout(
                dataItems = testData,
                testIdentifier = "testGrid",
                itemRenderer = { item ->
                    // Simple item renderer
                }
            )
        }

        composeTestRule.onNodeWithTag("testGrid").assertExists()
    }

    @Test
    fun gridLayout_displaysItems() {
        // Given
        val items = listOf("Item 1", "Item 2", "Item 3")

        // When
        composeTestRule.setContent {
            GridLayout(
                dataItems = items,
                testIdentifier = "testGrid",
                itemRenderer = { item ->
                    androidx.compose.material3.Text(text = item.toString())
                }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 3").assertIsDisplayed()
    }

    @Test
    fun gridLayout_withTestTag_displaysWithTag() {
        // Given
        val items = listOf("Item 1")
        val testTag = "testGrid"

        // When
        composeTestRule.setContent {
            GridLayout(
                dataItems = items,
                testIdentifier = testTag,
                itemRenderer = { item ->
                    androidx.compose.material3.Text(text = item.toString())
                }
            )
        }

        // Then
        composeTestRule.onNodeWithTag(testTag).assertIsDisplayed()
    }

    @Test
    fun gridLayout_withCustomColumns_displaysCorrectly() {
        // Given
        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")

        // When
        composeTestRule.setContent {
            GridLayout(
                dataItems = items,
                columnCount = 3,
                itemRenderer = { item ->
                    androidx.compose.material3.Text(text = item.toString())
                }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 3").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 4").assertIsDisplayed()
    }

    @Test
    fun gridLayout_withEmptyList_displaysNothing() {
        // Given
        val emptyItems = emptyList<String>()

        // When
        composeTestRule.setContent {
            GridLayout(
                dataItems = emptyItems,
                itemRenderer = { item ->
                    androidx.compose.material3.Text(text = item.toString())
                }
            )
        }

        // Then
        // Should not crash and should display empty grid
    }
} 