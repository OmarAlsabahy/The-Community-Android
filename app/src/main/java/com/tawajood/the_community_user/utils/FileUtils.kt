package com.tawajood.the_community_user.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class FileUtils {
    companion object {
        fun getMimeTypeFromContentResolver(context: Context, uri: Uri): String? {
            // Get the content resolver.
            val resolver = context.contentResolver

            // Get the MIME type from the content resolver.
            var mimeType = resolver.getType(uri)

            // If the MIME type is null, get the file extension and use the MimeTypeMap to get the MIME type.
            if (mimeType == null) {
                val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
                mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
            }

            // Return the MIME type.
            return mimeType
        }
        fun convertUriToFile(context: Context, uri: Uri): File? {
            // Get the cache directory.
            val cacheDir = context.cacheDir

            // Create a new file in the cache directory.


            // Copy the content from the URI to the file.
            val inputStream = context.contentResolver.openInputStream(uri)

            val ext = getMimeTypeFromContentResolver(context, uri) ?: "jpeg"

            if (inputStream != null) {
                val file = File(cacheDir, "temp-image-${System.currentTimeMillis()}")
                val fileOutputStream = FileOutputStream(file)
                val buffer = ByteArray(1024)
                var bytesRead: Int
                bytesRead = inputStream.read(buffer)
                while (bytesRead != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead)
                    bytesRead = inputStream.read(buffer)
                }

                // Close the streams.
                inputStream.close()
                fileOutputStream.close()
                return file

            }

            // Return the file.
            return null
        }
        fun convertFileToMultiPart(name: String,file: File): MultipartBody.Part{
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(name, file.name, requestFile)
        }
    }
}