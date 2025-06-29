package `in`.tarun.gallery.components

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun VideoPreview(
    modifier: Modifier = Modifier,
    videoPath: String,
    accessibilityDescription: String,
    cornerRadius: Int = 22,
    showPlayOverlay: Boolean = true,
    overlayIconSize: Int = 48
) {
    var thumbnailBitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(videoPath) {
        withContext(Dispatchers.IO) {
            try {
                val metadataRetriever = MediaMetadataRetriever()
                metadataRetriever.setDataSource(videoPath)
                thumbnailBitmap = metadataRetriever.getFrameAtTime(1_000_000)
                metadataRetriever.release()
            } catch (e: Exception) {
                thumbnailBitmap = null
            }
        }
    }

    if (thumbnailBitmap != null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(cornerRadius.dp)),
            contentAlignment = Alignment.Center
        ) {
            thumbnailBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = accessibilityDescription,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
            }
            if (showPlayOverlay) {
                Icon(
                    imageVector = Icons.Default.PlayCircle,
                    contentDescription = "Video preview",
                    modifier = Modifier.size(overlayIconSize.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(cornerRadius.dp))
                .background(Color.Cyan),
            contentAlignment = Alignment.Center
        ) {
            if (showPlayOverlay) {
                Icon(
                    imageVector = Icons.Default.PlayCircle,
                    contentDescription = "Video preview",
                    modifier = Modifier.size(overlayIconSize.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
} 