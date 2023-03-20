package com.example.searchmapuniversity.models.data.network.metro
import com.google.gson.annotations.SerializedName

data class MetroInfoDto(
    val id: String,
    @SerializedName("lines")
    val lineDtos: List<LineDto>,
    val name: String
)