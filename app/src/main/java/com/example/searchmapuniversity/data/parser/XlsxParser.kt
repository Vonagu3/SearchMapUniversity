package com.example.searchmapuniversity.data.parser
import java.io.InputStream


interface XlsxParser<T> {
    suspend fun parse(stream: InputStream): T
}