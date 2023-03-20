package com.example.searchmapuniversity.data.converter

import com.example.searchmapuniversity.models.data.network.yandex.FileItemDto
import com.example.searchmapuniversity.models.domain.yandex.UniversityFileItem
import javax.inject.Inject

class UniversityFileItemConverter @Inject constructor(): OneWayConverter<FileItemDto, UniversityFileItem> {
    override fun convert(from: FileItemDto) =
        UniversityFileItem(
            name = from.name,
            fileUrl = from.file
        )
}