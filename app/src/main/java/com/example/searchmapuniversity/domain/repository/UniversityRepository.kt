package com.example.searchmapuniversity.domain.repository
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.utils.Result
import com.example.searchmapuniversity.utils.UniFeedback
import kotlinx.coroutines.flow.Flow

interface UniversityRepository {
    fun getUniversities(fetchFromRemote: Boolean, query: String?): Flow<Result<List<UniversityInfoItem>>>
    fun getLikedUniversities(): Flow<Result<List<UniversityInfoItem>>>
    fun likeUniversity(universityInfoItem: UniversityInfoItem): Flow<Result<UniFeedback>>
}