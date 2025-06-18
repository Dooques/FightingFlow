package com.example.fightingflow.data.mediastore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

data class ImageObject(
    val uri: Uri? = null,
    val file: File? = null
)

class MediaStoreUtil(private val context: Context) {

    suspend fun createAndShareTempImage(bitmap: Bitmap): ImageObject  {
        Timber.d("Preparing to save or share image...")
        Timber.d("Launching Dispatcher.IO coroutine...")
        val imageObject: ImageObject = withContext(Dispatchers.IO) {
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

    fun copyImageToAppStorage(originalUri: Uri, characterName: String): Uri? {
        Timber.d("-- Copying image to app storage. --")
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        if (characterName.isBlank()) {
            Timber.e("No Name Found")
            return null
        }

        Timber.d("Sanitizing name for file")
        val sanitizedCharName = characterName.replace(Regex("[^a-zA-Z0-9.-]"), "")
        if (sanitizedCharName.isBlank()) {
            Timber.w("Sanitized name is blank, please enter a valid name.")
            return null
        }

        Timber.d("Attempting to get file from external storage...")
        try {
            Timber.d("File: $originalUri")
            inputStream = context.contentResolver.openInputStream(originalUri)
            if (inputStream == null) return null
            Timber.d("Input Stream: $inputStream")

            // Using getExternalFiles for debugging, but getFilesDir is more private
            val imageDir = File(context.getExternalFilesDir(null), "CharacterImages")
            Timber.d("Image Dir: $imageDir")
            if (!imageDir.exists()) {
                imageDir.mkdirs()
            }
            val outputFile = File(imageDir, "$sanitizedCharName.png")
            Timber.d("Output File: $outputFile")

            outputStream = FileOutputStream(outputFile)
            Timber.d("Output Stream: $outputStream")
            inputStream.copyTo(outputStream)

            return Uri.fromFile(outputFile)
        } catch (e: Exception) {
            Timber.e(e, "Error copying image to app storage.")
            return null
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    }

    fun finalizeImage(originalFile: File, characterName: String): Uri? {
        Timber.d("-- Finalizing Image --")

        if (originalFile.name.contains(characterName)) {
            return originalFile.absolutePath.toUri()
        }

        val imageDir = context.getExternalFilesDir("CharacterImages")
        if (imageDir == null) {
            Timber.e("Failed to get CharacterImages directory. External Storage might" +
                    " not be available")
            return null
        }

        if (!imageDir.exists()) {
            if (!imageDir.mkdirs()) {
                Timber.e("Failed to create CharacterImages directory at ${imageDir.absolutePath}")
            }
        }

        if (characterName.isBlank()) {
            Timber.e("No Name Found")
            return null
        }
        val sanitizedCharName = characterName.replace(Regex("[^a-zA-Z0-9.-]"), "")
        if (sanitizedCharName.isBlank()) {
            Timber.w("Sanitized name is blank, please enter a valid name.")
            return null
        }
        val finalFileName = "${sanitizedCharName}.png"
        val finalOutputFile = File(imageDir, finalFileName)

        Timber.d("Attempting to rename ${originalFile.absolutePath} to ${finalOutputFile.absolutePath}")

        if (finalOutputFile.exists()) {
            Timber.w("File with that name already exists: ${finalOutputFile.absolutePath}" +
                    "Deleting it before renaming.")
            if (!finalOutputFile.delete()) {
                Timber.e("Failed to delete final file. Cannot proceed with rename.")
                return null
            }
        }
        if (originalFile.renameTo(finalOutputFile)) {
            Timber.d("Successfully renamed to: ${finalOutputFile.absolutePath}")
            return Uri.fromFile(finalOutputFile)
        } else {
            Timber.d("Failed to rename file, copying and deleting as fallback.")
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                inputStream = FileInputStream(originalFile)
                outputStream = FileOutputStream(finalOutputFile)
                inputStream.copyTo(outputStream)
                Timber.d("Successfully copied to ${finalOutputFile.absolutePath}")

                if (!originalFile.delete()) {
                    Timber.w("Failed to delete original tempo file after copy: ${originalFile.absolutePath}")
                }
                return Uri.fromFile(finalOutputFile)
            } catch (e: Exception) {
                Timber.e("Error during fallback copy for $characterName")
                if (finalOutputFile.exists()) {
                    finalOutputFile.delete()
                }
                return null
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        }
    }

    fun getFileFromUri(uri: Uri): File? {
        Timber.d("-- Getting file from URI --")
        if (uri.scheme == "file") {
            uri.path?.let { path ->
                val file = File(path)
                if (file.exists()) {
                    Timber.d("File successfully found using URI: ${file.absolutePath}")
                    return file
                } else {
                    Timber.d("File URI points to non-existent file: $path")
                    return null
                }
            }
        } else {
            Timber.w("Cannot return file from this type of URI scheme.")
            return null
        }
        Timber.w("Error occurred while checking URI: ${uri.path}")
        return null
    }

    fun deleteImageFromAppStorage(fileName: String): Boolean {
        Timber.d("-- Deleting $fileName from storage --")
        if (fileName.isBlank()) {
            Timber.w("File name is blank, cannot delete")
            return false
        }

        val imageDir = File(context.getExternalFilesDir(null), "CharacterImages")
        if (!imageDir.exists()){
            Timber.i("Image Directory $imageDir does not exist, nothing to delete.")
            return false
        }

        val fileToDelete = File(imageDir, fileName)
        Timber.d("Attempting to delete $fileToDelete...")

        if (fileToDelete.exists()) {
            return try {
                if (fileToDelete.delete()) {
                    Timber.d("Successfully deleted file.")
                    true
                } else {
                    Timber.w("Failed to delete file.")
                    false
                }
            } catch(e: SecurityException) {
                Timber.e(e, "Security Exception while trying to delete file.")
                false
            } catch (e: Exception) {
                Timber.e(e, "Error while trying to delete file.")
                false
            }
        } else {
            Timber.i("${fileToDelete.absolutePath} doesn't exist. Nothing to delete.")
            return true
        }
    }

}