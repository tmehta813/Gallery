package `in`.tarun.gallery.feature.galleryview.domain

import `in`.tarun.gallery.feature.gallerydetails.domain.model.Album
import `in`.tarun.gallery.feature.gallerydetails.domain.repository.MediaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchAlbumCollectionUseCaseTest {

    private lateinit var useCase: FetchAlbumCollectionUseCase
    private lateinit var mockRepository: MediaRepository

    @Before
    fun setup() {
        mockRepository = mockk()
        useCase = FetchAlbumCollectionUseCase(mockRepository)
    }

    @Test
    fun `invoke should return albums from repository`() = runTest {
        // Given
        val expectedAlbums = listOf(
            Album("Test Album 1", 10, "test_uri_1", false),
            Album("Test Album 2", 5, "test_uri_2", true)
        )
        coEvery { mockRepository.getAllAlbums() } returns expectedAlbums

        // When
        val result = useCase()

        // Then
        assertEquals(expectedAlbums, result)
    }

    @Test
    fun `invoke should return empty list when repository returns empty list`() = runTest {
        // Given
        val emptyAlbums = emptyList<Album>()
        coEvery { mockRepository.getAllAlbums() } returns emptyAlbums

        // When
        val result = useCase()

        // Then
        assertEquals(emptyAlbums, result)
        assert(result.isEmpty())
    }

    @Test
    fun `invoke should propagate exception from repository`() = runTest {
        // Given
        val exception = Exception("Repository error")
        coEvery { mockRepository.getAllAlbums() } throws exception

        // When & Then
        try {
            useCase()
            assert(false) // Should not reach here
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }
} 