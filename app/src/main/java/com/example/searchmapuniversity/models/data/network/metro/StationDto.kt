package com.example.searchmapuniversity.models.data.network.metro

import com.google.gson.annotations.SerializedName

data class StationDto(
    val id: String,
    val lat: Double,
    val line: LineXDto,
    val lng: Double,
    val name: String,
    val order: Int
)