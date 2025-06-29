package `in`.tarun.gallery.feature.gallerydetails.presentation

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaItem
import `in`.tarun.gallery.components.VideoPreview

@Composable
fun MediaItemCard(
    mediaData: MediaItem,
    modifier: Modifier = Modifier,
    onItemTapped: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .testTag("mediaItem_${mediaData.name}")
            .clickable(onClick = onItemTapped)
    ) {
        when (mediaData.type) {
            `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaType.VIDEO -> {
                VideoPreview(
                    videoPath = mediaData.uri.toString(),
                    accessibilityDescription = mediaData.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
            `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaType.IMAGE -> {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(mediaData.uri)
                        .size(400)
                        .crossfade(true)
                        .placeholder(`in`.tarun.gallery.R.drawable.placeholder)
                        .error(`in`.tarun.gallery.R.drawable.placeholder)
                        .build(),
                    contentDescription = mediaData.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = mediaData.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1
        )
    }
}