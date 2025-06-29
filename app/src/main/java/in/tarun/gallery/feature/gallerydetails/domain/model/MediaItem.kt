package `in`.tarun.gallery.feature.gallerydetails.domain.model

import android.net.Uri

data class MediaItem(
    val id: Long,
    val uri: Uri,
    val mimeType: String,
    val name: String,
    val folderName: String,
    val type: MediaType
)