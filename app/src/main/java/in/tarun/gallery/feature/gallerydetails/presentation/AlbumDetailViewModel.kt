package `in`.tarun.gallery.feature.gallerydetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaItem
import `in`.tarun.gallery.feature.gallerydetails.domain.usecase.RetrieveAlbumContentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AlbumDetailViewModel @Inject constructor(
    private val albumContentsUseCase: RetrieveAlbumContentsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _screenState = MutableStateFlow<AlbumDetailUiState>(AlbumDetailUiState.Loading)
    val uiState: StateFlow<AlbumDetailUiState> = _screenState.asStateFlow()

    private val targetAlbumName: String = savedStateHandle["albumName"] ?: ""

    init {
        fetchAlbumContents()
    }

    private fun fetchAlbumContents() {
        viewModelScope.launch {
            try {
                val mediaCollection = albumContentsUseCase(targetAlbumName)
                _screenState.value = AlbumDetailUiState.Success(mediaCollection)
            } catch (exception: Exception) {
                _screenState.value = AlbumDetailUiState.Error(exception.message ?: "Unknown error")
            }
        }
    }
}

sealed class AlbumDetailUiState {
    object Loading : AlbumDetailUiState()
    data class Success(val media: List<MediaItem>) : AlbumDetailUiState()
    data class Error(val message: String) : AlbumDetailUiState()
}