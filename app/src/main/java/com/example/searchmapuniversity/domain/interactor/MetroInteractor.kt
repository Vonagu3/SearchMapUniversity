package com.example.searchmapuniversity.domain.interactor

import com.example.searchmapuniversity.models.domain.metro.MetroInfo
import kotlinx.coroutines.flow.Flow
import com.example.searchmapuniversity.utils.Result

interface MetroInteractor {
    fun fetchMetroInfo(fetchFromRemote: Boolean): Flow<Result<List<MetroInfo>>>
}