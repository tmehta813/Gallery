package `in`.tarun.gallery.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testListLayoutDisplays() {
        val testData = listOf("Item 1", "Item 2", "Item 3")
        
        composeTestRule.setContent {
            ListLayout(
                dataItems = testData,
                testIdentifier = "testList",
                itemRenderer = { item ->
                    // Simple item renderer
                }
            )
        }

        composeTestRule.onNodeWithTag("testList").assertExists()
    }

    @Test
    fun listLayout_displaysItems() {
        // Given
        val items = listOf("Item 1", "Item 2", "Item 3")

        // When
        composeTestRule.setContent {
            ListLayout(
                dataItems = items,
                testIdentifier = "testList",
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
    fun listLayout_withTestTag_displaysWithTag() {
        // Given
        val items = listOf("Item 1")
        val testTag = "testList"

        // When
        composeTestRule.setContent {
            ListLayout(
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
    fun listLayout_withEmptyList_displaysNothing() {
        // Given
        val emptyItems = emptyList<String>()

        // When
        composeTestRule.setContent {
            ListLayout(
                dataItems = emptyItems,
                testIdentifier = "testList",
                itemRenderer = { item ->
                    androidx.compose.material3.Text(text = item.toString())
                }
            )
        }

        // Then
        // Should not crash and should display empty list
    }

    @Test
    fun listLayout_withCustomEdgePadding_displaysCorrectly() {
        // Given
        val items = listOf("Item 1", "Item 2")

        // When
        composeTestRule.setContent {
            ListLayout(
                dataItems = items,
                testIdentifier = "testList",
                edgePadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                itemRenderer = { item ->
                    androidx.compose.material3.Text(text = item.toString())
                }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 2").assertIsDisplayed()
    }
} 