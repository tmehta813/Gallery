package `in`.tarun.gallery.feature.gallerydetails.domain.usecase

import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaItem
import `in`.tarun.gallery.feature.gallerydetails.domain.repository.MediaRepository
import `in`.tarun.gallery.feature.galleryview.domain.MediaDetailsRepo
import javax.inject.Inject

class RetrieveAlbumContentsUseCase @Inject constructor(
    private val mediaRepository: MediaDetailsRepo
) {
    suspend operator fun invoke(albumName: String): List<MediaItem> = mediaRepository.getListOfMedia(albumName)
}