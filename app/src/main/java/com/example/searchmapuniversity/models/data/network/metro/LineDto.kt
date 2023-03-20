package com.example.searchmapuniversity.models.data.network.metro

import com.google.gson.annotations.SerializedName

data class LineDto(
    @SerializedName("hex_color")
    val hexColor: String,
    val id: String,
    val name: String,
    @SerializedName("stations")
    val stationDtos: List<StationDto>
)