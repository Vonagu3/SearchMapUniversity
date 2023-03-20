package com.example.searchmapuniversity.models.data.network.yandex

import com.google.gson.annotations.SerializedName

data class FilesDto(
    @SerializedName("items")
    val fileItems: List<FileItemDto>
)