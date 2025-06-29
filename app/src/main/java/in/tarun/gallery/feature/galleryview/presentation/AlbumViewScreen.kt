package `in`.tarun.gallery.feature.galleryview.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import `in`.tarun.gallery.components.*
import `in`.tarun.gallery.core.permission.PermissionHelper

@Composable
fun AlbumListScreen(navController: NavController) {
    val viewModel: AlbumListViewModel = hiltViewModel()
    AlbumListScreen(navController = navController, viewModel = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumListScreen(navController: NavController,
                viewModel: AlbumListViewModel = hiltViewModel(),
                injectedUiState: AlbumListUiState? = null,
                permissionsOverride: Boolean = false
) {

    val observedUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiState = injectedUiState ?: observedUiState

    val currentDisplayMode = rememberSaveable { mutableStateOf(DisplayMode.Grid) }
    val permissionsGranted = remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (PermissionHelper.allPermissionsGranted(permissions)) {
            permissionsGranted.value = true
            viewModel.loadAlbums()
        }
        else{
            permissionsGranted.value = false
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(PermissionHelper.requiredPermissions())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GalleryApp") },
                actions = {
                    ViewModeToggle(
                        currentViewMode = currentDisplayMode.value,
                        onViewModeChanged = { currentDisplayMode.value = it }
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (!permissionsGranted.value && !permissionsOverride) {
                PermissionPromptCard(
                    onPermissionRequested = {
                        permissionLauncher.launch(PermissionHelper.requiredPermissions())
                    }
                )
            } else {
                when (uiState) {
                    is AlbumListUiState.Loading -> {
                        CircularLoader()
                    }

                    is AlbumListUiState.Success -> {
                        val albums = uiState.albums
                        when (currentDisplayMode.value) {
                            DisplayMode.Grid -> {
                                GridLayout(
                                    dataItems = albums,
                                    columnCount = 2,
                                    testIdentifier = "albumGrid",
                                    itemRenderer = { album ->
                                        AlbumCard(albumData = album as `in`.tarun.gallery.feature.gallerydetails.domain.model.Album) {
                                            navController.navigate("albumDetail/${album.name}")
                                        }
                                    }
                                )
                            }

                            DisplayMode.List -> {
                                ListLayout(
                                    dataItems = albums,
                                    testIdentifier = "albumList",
                                    itemRenderer = { album ->
                                        AlbumCard(albumData = album as `in`.tarun.gallery.feature.gallerydetails.domain.model.Album) {
                                            navController.navigate("albumDetail/${album.name}")
                                        }
                                    }
                                )
                            }
                        }
                    }

                    is AlbumListUiState.Error -> {
                        ErrorMessageCard(errorText = (uiState).message)
                    }
                }
            }
        }
    }
}