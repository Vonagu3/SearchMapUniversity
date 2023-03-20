package com.example.searchmapuniversity.models.data.network.yandex

import com.google.gson.annotations.SerializedName

data class FileItemDto(
    @SerializedName("file")
    val `file`: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("path")
    val path: String,
)