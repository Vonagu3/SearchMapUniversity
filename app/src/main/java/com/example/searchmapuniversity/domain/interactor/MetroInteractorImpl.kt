package com.example.searchmapuniversity.domain.interactor

import com.example.searchmapuniversity.domain.repository.MetroRepository
import com.example.searchmapuniversity.models.domain.metro.MetroInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.searchmapuniversity.utils.Result


class MetroInteractorImpl @Inject constructor(
    private val metroRepository: MetroRepository
): MetroInteractor {
    override fun fetchMetroInfo(fetchFromRemote: Boolean): Flow<Result<List<MetroInfo>>> {
        return metroRepository.getMetroInfo(fetchFromRemote)
    }
}