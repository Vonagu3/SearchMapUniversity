package com.example.searchmapuniversity.data.network

import com.example.searchmapuniversity.models.data.network.metro.MetroInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MetroApi {
    @GET("/metro/{city_id}")
    suspend fun getAllMetroInfo(@Path("city_id") cityId: Int): MetroInfoDto
}