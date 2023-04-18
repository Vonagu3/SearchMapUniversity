package com.example.searchmapuniversity.domain.interactor

import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.utils.Result
import com.example.searchmapuniversity.utils.UniFeedback
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UniversityInteractor {
    fun fetchUniversitiesInfo(fetchFromRemote: Boolean): Flow<Result<List<UniversityInfoItem>>>
//    suspend fun likeUniversity(universityInfoItem: UniversityInfoItem): Result<Boolean>
    fun likeUniversity(universityInfoItem: UniversityInfoItem): Flow<Result<UniFeedback>>
    fun fetchLikedUniversities(): Flow<Result<List<UniversityInfoItem>>>
}