package `in`.tarun.gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import `in`.tarun.gallery.core.navigation.GalleryNavGraph
import `in`.tarun.gallery.core.theme.GalleryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryAppTheme {
                val navController = rememberNavController()
                GalleryNavGraph(navController = navController)
            }
        }
    }
}