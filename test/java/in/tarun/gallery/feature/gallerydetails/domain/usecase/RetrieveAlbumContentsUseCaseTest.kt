package `in`.tarun.gallery.feature.gallerydetails.domain.usecase

import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaItem
import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaType
import `in`.tarun.gallery.feature.gallerydetails.domain.repository.MediaRepository
import `in`.tarun.gallery.TestUtils
import `in`.tarun.gallery.feature.galleryview.domain.MediaDetailsRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RetrieveAlbumContentsUseCaseTest {

    private lateinit var useCase: RetrieveAlbumContentsUseCase
    private lateinit var mockRepository: MediaDetailsRepo

    @Before
    fun setup() {
        mockRepository = mockk()
        useCase = RetrieveAlbumContentsUseCase(mockRepository)
    }

    @Test
    fun `invoke should return media items from repository`() = runTest {
        // Given
        val albumName = "Test Album"
        val testData1 = TestUtils.createTestMediaItemData(
            id = 1L,
            name = "image1.jpg",
            uriString = "content://test/image1",
            type = MediaType.IMAGE
        )
        val testData2 = TestUtils.createTestMediaItemData(
            id = 2L,
            name = "video1.mp4",
            uriString = "content://test/video1",
            type = MediaType.VIDEO
        )
        
        // Create mock MediaItems using the test data
        val expectedMediaItems = listOf(
            MediaItem(
                id = testData1.id,
                uri = mockk(relaxed = true),
                mimeType = testData1.mimeType,
                name = testData1.name,
                folderName = testData1.folderName,
                type = testData1.type
            ),
            MediaItem(
                id = testData2.id,
                uri = mockk(relaxed = true),
                mimeType = testData2.mimeType,
                name = testData2.name,
                folderName = testData2.folderName,
                type = testData2.type
            )
        )
        coEvery { mockRepository.getListOfMedia(albumName) } returns expectedMediaItems

        // When
        val result = useCase(albumName)

        // Then
        assertEquals(expectedMediaItems, result)
    }

    @Test
    fun `invoke should return empty list when repository returns empty list`() = runTest {
        // Given
        val albumName = "Empty Album"
        val emptyMediaItems = emptyList<MediaItem>()
        coEvery { mockRepository.getListOfMedia(albumName) } returns emptyMediaItems

        // When
        val result = useCase(albumName)

        // Then
        assertEquals(emptyMediaItems, result)
        assert(result.isEmpty())
    }

    @Test
    fun `invoke should propagate exception from repository`() = runTest {
        // Given
        val albumName = "Error Album"
        val exception = Exception("Repository error")
        coEvery { mockRepository.getListOfMedia(albumName) } throws exception

        // When & Then
        try {
            useCase(albumName)
            assert(false) // Should not reach here
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }

    @Test
    fun `invoke should handle empty album name`() = runTest {
        // Given
        val emptyAlbumName = ""
        val expectedMediaItems = listOf<MediaItem>()
        coEvery { mockRepository.getListOfMedia(emptyAlbumName) } returns expectedMediaItems

        // When
        val result = useCase(emptyAlbumName)

        // Then
        assertEquals(expectedMediaItems, result)
    }
} 