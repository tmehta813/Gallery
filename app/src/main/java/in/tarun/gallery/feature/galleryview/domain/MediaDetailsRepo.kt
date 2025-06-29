package `in`.tarun.gallery.feature.galleryview.domain

import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaItem

interface MediaDetailsRepo {
    suspend fun getListOfMedia(albumName: String): List<MediaItem>
}