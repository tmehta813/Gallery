package `in`.tarun.gallery.feature.galleryview.presentation

import `in`.tarun.gallery.feature.gallerydetails.domain.model.Album
import `in`.tarun.gallery.feature.galleryview.domain.FetchAlbumCollectionUseCase
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
class AlbumListViewModelTest {

    private lateinit var viewModel: AlbumListViewModel
    private lateinit var mockUseCase: FetchAlbumCollectionUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockUseCase = mockk()
        viewModel = AlbumListViewModel(mockUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        assertTrue(viewModel.uiState.value is AlbumListUiState.Loading)
    }

    @Test
    fun `loadAlbums should emit Success state when use case returns albums`() = runTest {

        val mockAlbums = listOf(
            Album("Test Album 1", 10, "test_uri_1", false),
            Album("Test Album 2", 5, "test_uri_2", true)
        )
        coEvery { mockUseCase() } returns mockAlbums

        viewModel.loadAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        val currentState = viewModel.uiState.value
        assertTrue(currentState is AlbumListUiState.Success)
        assertEquals(mockAlbums, (currentState as AlbumListUiState.Success).albums)
    }

    @Test
    fun `loadAlbums should emit Error state when use case throws exception`() = runTest {

        val errorMessage = "Network error"
        coEvery { mockUseCase() } throws Exception(errorMessage)

        viewModel.loadAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        val currentState = viewModel.uiState.value
        assertTrue(currentState is AlbumListUiState.Error)
        assertEquals(errorMessage, (currentState as AlbumListUiState.Error).message)
    }

    @Test
    fun `loadAlbums should emit Error state with empty message when exception has no message`() = runTest {
        coEvery { mockUseCase() } throws Exception()

        viewModel.loadAlbums()
        testDispatcher.scheduler.advanceUntilIdle()

        val currentState = viewModel.uiState.value
        assertTrue(currentState is AlbumListUiState.Error)
        assertEquals("", (currentState as AlbumListUiState.Error).message)
    }
} 