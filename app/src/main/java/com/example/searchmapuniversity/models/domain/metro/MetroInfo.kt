package com.example.searchmapuniversity.models.domain.metro

data class MetroInfo(
    val id: String,
    val hexColor: String,
    val name: String,
    val stations: List<StationInfo>
)
