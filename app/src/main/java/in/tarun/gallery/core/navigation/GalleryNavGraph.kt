package `in`.tarun.gallery.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import `in`.tarun.gallery.feature.gallerydetails.presentation.AlbumDetailScreen
import `in`.tarun.gallery.feature.galleryview.presentation.AlbumListScreen

@Composable
fun GalleryNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "album") {
        composable("album") {
            AlbumListScreen(navController)
        }

        composable("albumDetail/{albumName}") { backStackEntry ->
              AlbumDetailScreen()
        }
    }
}