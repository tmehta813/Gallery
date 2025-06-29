package `in`.tarun.gallery.feature.gallerydetails.data.repository

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.tarun.gallery.feature.gallerydetails.domain.model.Album
import `in`.tarun.gallery.feature.gallerydetails.domain.repository.MediaRepository

class MediaRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : MediaRepository {

    override suspend fun getAllAlbums(): List<Album> {

        val contentResolver: ContentResolver = appContext.contentResolver

        val queryColumns = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.MIME_TYPE
        )
        val contentUri = MediaStore.Files.getContentUri("external")

        val filterClause = ("${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR " +
                "${MediaStore.Files.FileColumns.MEDIA_TYPE}=?")

        val filterArguments = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )

        val folderMap = mutableMapOf<String, MutableList<Pair<String, String>>>()
        val imageCollection = mutableListOf<String>()
        val videoCollection = mutableListOf<String>()

        contentResolver.query(
            contentUri,
            queryColumns,
            filterClause,
            filterArguments,
            "${MediaStore.MediaColumns.DATE_ADDED} DESC"
        )?.use { cursor ->
            val folderColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)
            val pathColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            val mimeColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)

            while (cursor.moveToNext()) {
                val folderName = cursor.getString(folderColumnIndex) ?: "Unknown"
                val filePath = cursor.getString(pathColumnIndex)
                val mimeType = cursor.getString(mimeColumnIndex) ?: ""

                val fileType = if (mimeType.startsWith("image")) {
                    imageCollection.add(filePath)
                    "image"
                } else if (mimeType.startsWith("video")) {
                    videoCollection.add(filePath)
                    "video"
                } else continue

                folderMap.getOrPut(folderName) { mutableListOf() }.add(filePath to fileType)
            }
        }

        val regularAlbums = folderMap.map { (folderName, fileList) ->
            val imageThumbnail = fileList.firstOrNull { it.second == "image" }?.first
            val videoThumbnail = fileList.firstOrNull { it.second == "video" }?.first
            val thumbnailPath = imageThumbnail ?: videoThumbnail
            val isVideoThumb = imageThumbnail == null && videoThumbnail != null

            Album(
                name = folderName,
                itemCount = fileList.size,
                thumbnailUri = thumbnailPath.orEmpty(),
                isVideoThumbnail = isVideoThumb
            )
        }

        val specialAlbums = mutableListOf<Album>()
        if (imageCollection.isNotEmpty()) {
            specialAlbums.add(Album("All Images", imageCollection.size, imageCollection.first()))
        }
        if (videoCollection.isNotEmpty()) {
            specialAlbums.add(Album("All Videos", videoCollection.size, videoCollection.first(), isVideoThumbnail = true))
        }

        return (specialAlbums + regularAlbums).sortedByDescending { it.itemCount }
    }
} 