package com.example.sample.ISP

import java.io.Closeable
import java.io.IOException

class CloseUtils {
    companion object {
        fun closeQuietly(closeable: Closeable?) {
            if (null != closeable) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}