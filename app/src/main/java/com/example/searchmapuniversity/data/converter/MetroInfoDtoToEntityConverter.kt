package com.example.searchmapuniversity.data.converter

import com.example.searchmapuniversity.models.data.local.MetroInfoEntity
import com.example.searchmapuniversity.models.data.local.MetroLineEntity
import com.example.searchmapuniversity.models.data.local.MetroStationEntity
import com.example.searchmapuniversity.models.data.network.metro.LineDto
import javax.inject.Inject

class MetroInfoDtoToEntityConverter @Inject constructor(): OneWayConverter<LineDto, MetroInfoEntity> {
    override fun convert(from: LineDto): MetroInfoEntity =
        MetroInfoEntity(
            MetroLineEntity(
                id = from.id,
                name = from.name,
                hexColor = from.hexColor
            ),
            from.stationDtos.filter { it.line.id == from.id }.map {
                MetroStationEntity(
                    name = it.name,
                    order = it.order,
                    lat = it.lat,
                    lng = it.lng,
                    lineId = from.id.toIntOrNull() ?: -1
                )
            }
        )
}