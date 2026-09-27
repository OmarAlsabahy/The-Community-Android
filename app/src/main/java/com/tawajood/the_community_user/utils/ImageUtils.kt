package com.tawajood.the_community_user.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

fun compressImageToSize(file: File, maxSize: Long = 2 * 1024 * 1024): File {
    try {
        if (!file.exists()) {
            throw FileNotFoundException("File does not exist")
        }

        val originalBitmap = BitmapFactory.decodeFile(file.path)
        var low = 0
        var high = 100
        var bestQuality = high
        var bestFile: File? = null

        while (low <= high) {
            val mid = (low + high) / 2
            val compressedFile = compressWithQuality(originalBitmap, mid)

            if (compressedFile.length() <= maxSize) {
                bestQuality = mid
                bestFile = compressedFile
                low = mid + 1 // Try for better quality
            } else {
                high = mid - 1 // Reduce size
            }
        }

        return bestFile ?: throw IOException("Unable to compress image to desired size")
    } catch (e: Exception) {
        e.printStackTrace()
        return file
    }
}

fun compressWithQuality(bitmap: Bitmap, quality: Int): File {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

    val compressedFile = File.createTempFile("compressed_", ".jpg")
    val fileOutputStream = BufferedOutputStream(FileOutputStream(compressedFile))
    fileOutputStream.write(outputStream.toByteArray())

    outputStream.close()
    fileOutputStream.flush()
    fileOutputStream.close()

    return compressedFile
}
