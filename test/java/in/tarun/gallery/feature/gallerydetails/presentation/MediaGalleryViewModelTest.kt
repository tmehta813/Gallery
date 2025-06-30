package `in`.tarun.gallery.feature.gallerydetails.presentation

import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaItem
import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaType
import `in`.tarun.gallery.feature.gallerydetails.domain.usecase.RetrieveAlbumContentsUseCase
import `in`.tarun.gallery.TestUtils
import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumDetailViewModelTest {

    private lateinit var viewModel: AlbumDetailViewModel
    private lateinit var mockUseCase: RetrieveAlbumContentsUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockUseCase = mockk()
        savedStateHandle = SavedStateHandle(mapOf("albumName" to "Test Album"))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        // Given
        coEvery { mockUseCase("Test Album") } returns emptyList()
        
        // When
        viewModel = AlbumDetailViewModel(mockUseCase, savedStateHandle)
        
        // Then
        assertTrue(viewModel.uiState.value is AlbumDetailUiState.Loading)
    }

    @Test
    fun `should load media items successfully`() = runTest {
        // Given
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

        val mockMediaItems = listOf(
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
        coEvery { mockUseCase("Test Album") } returns mockMediaItems

        // When
        viewModel = AlbumDetailViewModel(mockUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assertTrue(currentState is AlbumDetailUiState.Success)
        assertEquals(mockMediaItems, (currentState as AlbumDetailUiState.Success).media)
    }

    @Test
    fun `should emit Error state when use case throws exception`() = runTest {
        // Given
        val errorMessage = "Failed to load media"
        coEvery { mockUseCase("Test Album") } throws Exception(errorMessage)

        // When
        viewModel = AlbumDetailViewModel(mockUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assertTrue(currentState is AlbumDetailUiState.Error)
        assertEquals(errorMessage, (currentState as AlbumDetailUiState.Error).message)
    }

    @Test
    fun `should emit Error state with unknown error when exception has no message`() = runTest {
        // Given
        coEvery { mockUseCase("Test Album") } throws Exception()

        // When
        viewModel = AlbumDetailViewModel(mockUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assertTrue(currentState is AlbumDetailUiState.Error)
        assertEquals("Unknown error", (currentState as AlbumDetailUiState.Error).message)
    }

    @Test
    fun `should handle empty album name`() = runTest {
        // Given
        val emptySavedStateHandle = SavedStateHandle(mapOf("albumName" to ""))
        coEvery { mockUseCase("") } throws Exception("Empty album name")

        // When
        val viewModelWithEmptyAlbum = AlbumDetailViewModel(mockUseCase, emptySavedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModelWithEmptyAlbum.uiState.value
        assertTrue(currentState is AlbumDetailUiState.Error)
        assertEquals("Empty album name", (currentState as AlbumDetailUiState.Error).message)
    }
} 