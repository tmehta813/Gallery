package `in`.tarun.gallery.feature.gallerydetails.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import `in`.tarun.gallery.components.*
import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaItem

@Composable
fun AlbumDetailScreen(
    albumName: String,
    navController: NavController,
) {
    val viewModel: AlbumDetailViewModel = hiltViewModel()
    AlbumDetailScreen(albumName, navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    albumName: String,
    navController: NavController,
    viewModel: AlbumDetailViewModel = hiltViewModel(),
    injectedUiState: AlbumDetailUiState? = null
) {
    val observedState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiState = injectedUiState ?: observedState
    val currentDisplayMode = rememberSaveable { mutableStateOf(DisplayMode.Grid) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = albumName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    ViewModeToggle(
                        currentViewMode = currentDisplayMode.value,
                        onViewModeChanged = { currentDisplayMode.value = it }
                    )
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when (uiState) {
                is AlbumDetailUiState.Loading -> {
                    CircularLoader()
                }

                is AlbumDetailUiState.Error -> {
                    ErrorMessageCard(errorText = uiState.message)
                }

                is AlbumDetailUiState.Success -> {
                    val media = uiState.media
                    if (media.isEmpty()) {
                        Text("No media found", modifier = Modifier.align(Alignment.Center))
                    } else {
                        when (currentDisplayMode.value) {
                            DisplayMode.Grid -> {
                                GridLayout(
                                    dataItems = media,
                                    columnCount = 3,
                                    itemRenderer = { item ->
                                        MediaItemCard(item as MediaItem)
                                    }
                                )
                            }

                            DisplayMode.List -> {
                                ListLayout(
                                    dataItems = media,
                                    itemRenderer = { item ->
                                        MediaItemCard(item as MediaItem)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}