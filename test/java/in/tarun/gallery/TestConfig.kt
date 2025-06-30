package `in`.tarun.gallery

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import `in`.tarun.gallery.feature.gallerydetails.domain.model.Album
import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaType

/**
 * Test rule for managing coroutines in tests
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainCoroutineRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    
    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

/**
 * Test utilities for common test operations
 */
object TestUtils {
    
    /**
     * Creates a test album with default values
     */
    fun createTestAlbum(
        name: String = "Test Album",
        itemCount: Int = 10,
        thumbnailUri: String? = "test_uri",
        isVideoThumbnail: Boolean = false
    ) = Album(
        name = name,
        itemCount = itemCount,
        thumbnailUri = thumbnailUri,
        isVideoThumbnail = isVideoThumbnail
    )

    /**
     * Creates test data for MediaItem without requiring android.net.Uri
     * This is used for unit tests where Android framework classes are not available
     */
    fun createTestMediaItemData(
        id: Long = 1L,
        name: String = "test_media.jpg",
        uriString: String = "content://test/media",
        mimeType: String = "image/jpeg",
        folderName: String = "Test Folder",
        type: MediaType = MediaType.IMAGE
    ) = TestMediaItemData(
        id = id,
        uriString = uriString,
        mimeType = mimeType,
        name = name,
        folderName = folderName,
        type = type
    )
}

/**
 * Test data class for MediaItem that doesn't require android.net.Uri
 */
data class TestMediaItemData(
    val id: Long,
    val uriString: String,
    val mimeType: String,
    val name: String,
    val folderName: String,
    val type: MediaType
) 