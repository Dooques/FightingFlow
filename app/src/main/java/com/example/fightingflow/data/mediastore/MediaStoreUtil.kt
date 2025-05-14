package com.example.fightingflow.data.mediastore

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.FileInputStream
import java.io.FileOutputStream

data class ImageObject(
    val uri: Uri? = null,
    val file: File? = null
)

class MediaStoreUtil(private val context: Context) {

    suspend fun createAndShareTempImage(bitmap: Bitmap): ImageObject?  {
        Timber.d("Preparing to save or share image...")
        Timber.d("Launching Dispatcher.IO coroutine...")
        val imageObject: ImageObject? = withContext(Dispatchers.IO) {
            Timber.d("thread launched, getting cache directory")
            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs()
            Timber.d("Cache Path received: $cachePath")
            Timber.d("Creating image file...")
            val imageFile = File(cachePath, "${System.currentTimeMillis()}.png")
            Timber.d("Image File created: $imageFile")

            Timber.d("Preparing to compress image as JPEG and share")
            try {
                Timber.d("Getting file output stream using file...")
                val stream = FileOutputStream(imageFile)
                Timber.d("Compressing file to jpeg...")
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                Timber.d("File compressed, closing file...")
                stream.close()
                Timber.d("File closed, jpeg compression completed")

                Timber.d("Getting image URI...")
                val imageUri: Uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    imageFile
                )
                Timber.d("Image URI received: $imageUri")

                Timber.d("Preparing to save or share image...")
                val dataToReturn = listOf(imageUri, imageFile)
                return@withContext ImageObject(imageUri, imageFile)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext ImageObject()
            }
        }
        return imageObject
    }

    suspend fun saveImageToGallery(imageUri: Uri) {
        withContext(Dispatchers.IO) {
            try {
                val contentResolver = context.contentResolver
                val fileName = "${System.currentTimeMillis()}.jpg"
                val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                } else {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }

                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(MediaStore.Images.Media.IS_PENDING, 1)
                    }
                }

                val uri = contentResolver.insert(imageCollection, contentValues)

                uri?.let {
                    contentResolver.openOutputStream(it)?.use { outputStream ->
                        val inputStream = FileInputStream(
                            context.contentResolver.openFileDescriptor(
                                imageUri, "r"
                            )?.fileDescriptor
                        )
                        inputStream.copyTo(outputStream)
                        inputStream.close()
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        contentValues.clear()
                        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                        contentResolver.update(it, contentValues, null, null)
                    }
                }
                // Show a success message to the user
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error
            }
        }
    }

    fun shareImage(activity: Activity, imageUri: Uri) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, imageUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val chooserIntent = Intent.createChooser(shareIntent, "Share Combo Image")
        activity.startActivity(chooserIntent)
    }

    suspend fun saveImageToUri(activity: Activity, sourceUri: Uri, destinationUri: Uri) {
        withContext(Dispatchers.IO) {
            try {
                activity.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
                    activity.contentResolver.openOutputStream(destinationUri)?.use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}