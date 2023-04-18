package com.example.searchmapuniversity.models.domain.diff

/**
 *  Payload для [LikedUniversitiesListAdapter]
 *
 *  @property likeChanged изменился ли like у элемента списка с университетами
 */
data class UniversityListPayload(
    val likeChanged: Boolean = true
)
