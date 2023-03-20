package com.example.searchmapuniversity.domain.repository
import com.example.searchmapuniversity.models.domain.metro.MetroInfo
import com.example.searchmapuniversity.utils.Result
import kotlinx.coroutines.flow.Flow

interface MetroRepository {
    fun getMetroInfo(fetchFromRemote: Boolean): Flow<Result<List<MetroInfo>>>
}