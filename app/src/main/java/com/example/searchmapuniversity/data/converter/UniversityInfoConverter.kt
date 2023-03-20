package com.example.searchmapuniversity.data.converter

import com.example.searchmapuniversity.models.data.local.UniversityInfoEntity
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import javax.inject.Inject

class UniversityInfoConverter @Inject constructor(): TwoWayConverter<UniversityInfoEntity, UniversityInfoItem> {
    override fun convert(from: UniversityInfoEntity) =
        UniversityInfoItem(
            name = from.name,
            logo = from.logo,
            address = from.address,
            lat = from.lat,
            lon = from.lon,
            site = from.site,
            like = from.like
        )

    override fun reverse(to: UniversityInfoItem) =
        UniversityInfoEntity(
            name = to.name,
            logo = to.logo,
            address = to.address,
            lat = to.lat,
            lon = to.lon,
            site = to.site,
            like = to.like
        )
}