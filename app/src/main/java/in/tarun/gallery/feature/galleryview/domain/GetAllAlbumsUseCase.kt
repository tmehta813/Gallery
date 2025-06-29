package `in`.tarun.gallery.feature.galleryview.domain

import `in`.tarun.gallery.feature.gallerydetails.domain.model.Album
import `in`.tarun.gallery.feature.gallerydetails.domain.repository.MediaRepository
import `in`.tarun.gallery.feature.galleryview.data.MediaDetailsRepoImp
import javax.inject.Inject

class FetchAlbumCollectionUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(): List<Album> {
        return mediaRepository.getAllAlbums()
    }
}