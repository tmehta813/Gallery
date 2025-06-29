package `in`.tarun.gallery.feature.galleryview.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import `in`.tarun.gallery.feature.gallerydetails.domain.model.Album
import `in`.tarun.gallery.components.VideoPreview

@Composable
fun AlbumCard(
    albumData: Album,
    modifier: Modifier = Modifier,
    onCardTapped: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(7.dp)
            .testTag("albumCard_${albumData.name}")
            .clickable(onClick = onCardTapped)
    ) {
        if(albumData.thumbnailUri != null && !albumData.isVideoThumbnail) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(albumData.thumbnailUri)
                    .size(600)
                    .crossfade(true)
                    .placeholder(`in`.tarun.gallery.R.drawable.placeholder)
                    .error(`in`.tarun.gallery.R.drawable.placeholder)
                    .build(),
                contentDescription = albumData.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // square
                    .clip(RoundedCornerShape(22.dp)),
                contentScale = ContentScale.Crop
            )
        } else if (albumData.thumbnailUri != null) {
            VideoPreview(
                videoPath = albumData.thumbnailUri,
                accessibilityDescription = albumData.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color.Cyan)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = albumData.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1
        )

        Text(
            text = "${albumData.itemCount} items",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}