package com.example.searchmapuniversity.utils

import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

object FileCaching {
    private const val CACHE_PATH = "/data/data/com.example.searchmapuniversity/cache"

    fun save(stream: InputStream, filePrefix: String): String {
        val cacheDir = File(CACHE_PATH)
        if (cacheDir.listFiles().isNotEmpty()) {
            cacheDir.walk().forEach {
                val splitPath = it.toString().split('/')
                if (splitPath[splitPath.size - 1].startsWith(filePrefix)) {
                    Files.delete(it.toPath())
                    return getFilePath(cachePathStr = CACHE_PATH, filePrefix = filePrefix, stream = stream)
                }
            }
        }
        return getFilePath(cachePathStr = CACHE_PATH, filePrefix = filePrefix, stream = stream)
    }

    private fun getFilePath(cachePathStr: String, filePrefix: String, stream: InputStream): String {
        val cachePath = Paths.get(cachePathStr)
        val filePath = Files.createTempFile(cachePath, filePrefix, ".xlsx")
        Files.write(filePath, stream.readBytes()).toString()
        return filePath.toString()
    }
}