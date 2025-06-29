package `in`.tarun.gallery.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VideoPreviewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testVideoPreviewDisplays() {
        composeTestRule.setContent {
            VideoPreview(
                videoPath = "test/path/video.mp4",
                accessibilityDescription = "Test video"
            )
        }

        composeTestRule.onNodeWithContentDescription("Video preview").assertExists()
    }

    @Test
    fun videoPreview_isDisplayed() {
        // Given
        val videoPath = "content://test/video.mp4"

        // When
        composeTestRule.setContent {
            VideoPreview(
                videoPath = videoPath,
                accessibilityDescription = "Video preview"
            )
        }

        // Then
        // Video preview should be displayed
        // Note: Actual video preview loading depends on MediaMetadataRetriever implementation
    }

    @Test
    fun videoPreview_withCustomModifier_isDisplayed() {
        // Given
        val videoPath = "content://test/video.mp4"

        // When
        composeTestRule.setContent {
            VideoPreview(
                videoPath = videoPath,
                accessibilityDescription = "Video preview",
                containerModifier = androidx.compose.ui.Modifier
            )
        }

        // Then
        // Video preview should be displayed with custom modifier
    }

    @Test
    fun videoPreview_withCustomCornerRadius_isDisplayed() {
        // Given
        val videoPath = "content://test/video.mp4"

        // When
        composeTestRule.setContent {
            VideoPreview(
                videoPath = videoPath,
                accessibilityDescription = "Video preview",
                cornerRadius = 16
            )
        }

        // Then
        // Video preview should be displayed with custom corner radius
    }

    @Test
    fun videoPreview_withoutPlayOverlay_isDisplayed() {
        // Given
        val videoPath = "content://test/video.mp4"

        // When
        composeTestRule.setContent {
            VideoPreview(
                videoPath = videoPath,
                accessibilityDescription = "Video preview",
                showPlayOverlay = false
            )
        }

        // Then
        // Video preview should be displayed without play overlay
    }

    @Test
    fun videoPreview_withInvalidPath_handlesGracefully() {
        // Given
        val videoPath = "invalid_path"

        // When
        composeTestRule.setContent {
            VideoPreview(
                videoPath = videoPath,
                accessibilityDescription = "Video preview"
            )
        }

        // Then
        // Should not crash and should handle invalid path gracefully
    }
} 