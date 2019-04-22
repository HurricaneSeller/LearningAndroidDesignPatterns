package com.example.sample.ISP

import android.graphics.Bitmap

import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class IOSample {
    fun put(url: String, bitmap: Bitmap) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(url + CACHE_DIE)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    companion object {
        private val CACHE_DIE = "CACHE_DIR"
    }
}
