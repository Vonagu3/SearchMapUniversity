package com.example.searchmapuniversity.data.converter
import com.example.searchmapuniversity.models.data.local.MetroLineEntity
import com.example.searchmapuniversity.models.data.local.MetroStationEntity
import com.example.searchmapuniversity.models.domain.metro.MetroInfo
import com.example.searchmapuniversity.models.domain.metro.StationInfo
import javax.inject.Inject

class MetroInfoEntityToDomainConverter @Inject constructor(): OneWayConverter<Map<MetroLineEntity, List<MetroStationEntity>>, List<MetroInfo>> {
    override fun convert(from: Map<MetroLineEntity, List<MetroStationEntity>>): List<MetroInfo> =
        from.map {
            MetroInfo(
                id = it.key.id,
                name = it.key.name,
                hexColor = it.key.hexColor,
                stations = it.value.map {
                    StationInfo(
                        name = it.name,
                        order = it.order,
                        lat = it.lat,
                        lng = it.lng
                    )
                }
            )
        }
}