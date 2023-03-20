package com.example.searchmapuniversity.data.network

import com.example.searchmapuniversity.models.data.network.yandex.FilesDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url

interface YandexCloudApi {

    @GET("/v1/disk/resources/files")
    suspend fun getAllFiles(@Header("Authorization") token: String): FilesDto

    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): ResponseBody
}