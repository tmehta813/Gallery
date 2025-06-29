package `in`.tarun.gallery.feature.galleryview.data

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.tarun.gallery.core.util.ALL_IMAGES
import `in`.tarun.gallery.core.util.ALL_VIDEOS
import `in`.tarun.gallery.core.util.DESC
import `in`.tarun.gallery.core.util.IMAGE
import `in`.tarun.gallery.core.util.VIDEO
import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaItem
import `in`.tarun.gallery.feature.gallerydetails.domain.model.MediaType
import `in`.tarun.gallery.feature.galleryview.domain.MediaDetailsRepo
import javax.inject.Inject

class MediaDetailsRepoImp @Inject constructor(
    @ApplicationContext private val appContext: Context
) :  MediaDetailsRepo {

    override suspend fun getListOfMedia(albumName: String): List<MediaItem> {
        val contentResolver: ContentResolver = appContext.contentResolver

        val queryColumns = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.BUCKET_DISPLAY_NAME,
        )
        val contentUri = MediaStore.Files.getContentUri("external")

        val filterClause: String
        val filterArguments: Array<String>

        when (albumName) {
            ALL_IMAGES -> {
                filterClause = "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ?"
                filterArguments = arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString())
            }
            ALL_VIDEOS -> {
                filterClause = "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ?"
                filterArguments = arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString())
            }
            else -> {
                filterClause = "${MediaStore.MediaColumns.BUCKET_DISPLAY_NAME} = ?"
                filterArguments = arrayOf(albumName)
            }
        }

        val mediaCollection = mutableListOf<MediaItem>()

        contentResolver.query(
            contentUri,
            queryColumns,
            filterClause,
            filterArguments,
            "${MediaStore.MediaColumns.DATE_ADDED} $DESC"
        )?.use { cursor ->
            val idColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            val nameColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            val pathColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            val mimeColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)
            val bucketColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val mediaId = cursor.getLong(idColumnIndex)
                val fileName = cursor.getString(nameColumnIndex)
                val filePath = cursor.getString(pathColumnIndex)
                val mimeType = cursor.getString(mimeColumnIndex)
                val folderName = cursor.getString(bucketColumnIndex)

                val mediaType = when {
                    mimeType.startsWith(IMAGE) -> MediaType.IMAGE
                    mimeType.startsWith(VIDEO) -> MediaType.VIDEO
                    else -> continue
                }

                val contentUri = "file://$filePath".toUri()

                mediaCollection.add(
                    MediaItem(
                        id = mediaId,
                        uri = contentUri,
                        mimeType = mimeType,
                        name = fileName,
                        folderName = folderName,
                        type = mediaType
                    )
                )
            }
        }

        return mediaCollection
    }

}