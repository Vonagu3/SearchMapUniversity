package com.example.searchmapuniversity.domain.interactor
import com.example.searchmapuniversity.domain.repository.UniversityRepository
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.utils.Result
import com.example.searchmapuniversity.utils.UniFeedback
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UniversityInteractorImpl @Inject constructor(
    private val universityRepository: UniversityRepository
): UniversityInteractor {
    override fun fetchUniversitiesInfo(fetchFromRemote: Boolean, query: String?): Flow<Result<List<UniversityInfoItem>>> {
        return universityRepository.getUniversities(fetchFromRemote, query)
    }

    override fun likeUniversity(universityInfoItem: UniversityInfoItem): Flow<Result<UniFeedback>> {
        return universityRepository.likeUniversity(universityInfoItem)
    }

    override fun fetchLikedUniversities(): Flow<Result<List<UniversityInfoItem>>> {
        return universityRepository.getLikedUniversities()
    }

//    override suspend fun likeUniversity(universityInfoItem: UniversityInfoItem): Result<Boolean> {
//        return universityRepository.likeUniversity(universityInfoItem)
//    }

//    override fun likeUniversity(universityInfoItem: UniversityInfoItem): Flow<Result<Boolean>> {
//        return universityRepository.likeUniversity(universityInfoItem)
//    }
}