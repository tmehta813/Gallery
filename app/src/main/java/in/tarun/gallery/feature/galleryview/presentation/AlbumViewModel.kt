package `in`.tarun.gallery.feature.galleryview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.tarun.gallery.feature.gallerydetails.domain.model.Album
import `in`.tarun.gallery.feature.galleryview.domain.FetchAlbumCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AlbumListViewModel @Inject constructor(
    private val albumCollectionUseCase: FetchAlbumCollectionUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<AlbumListUiState>(AlbumListUiState.Loading)
    val uiState: StateFlow<AlbumListUiState> = _screenState.asStateFlow()

    fun loadAlbums() {
        viewModelScope.launch {
            try {
                val albumCollection = albumCollectionUseCase()
                _screenState.value = AlbumListUiState.Success(albumCollection)
            } catch (exception: Exception) {
                _screenState.value = AlbumListUiState.Error(exception.message.orEmpty())
            }
        }
    }
}

sealed class AlbumListUiState {
    data object Loading : AlbumListUiState()
    data class Success(val albums: List<Album>) : AlbumListUiState()
    data class Error(val message: String) : AlbumListUiState()
}