package com.example.searchmapuniversity.models.data.local

data class MetroInfoEntity(
    val lineInfo: MetroLineEntity,
    val stationEntities: List<MetroStationEntity>
)