package `in`.tarun.gallery.feature.gallerydetails.domain.repository

import `in`.tarun.gallery.feature.gallerydetails.domain.model.Album
import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaItem

interface MediaRepository {
    suspend fun getAllAlbums(): List<Album>
}