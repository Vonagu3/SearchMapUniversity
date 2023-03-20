package com.example.searchmapuniversity.data.repository
import android.database.sqlite.SQLiteException
import com.example.searchmapuniversity.data.converter.MetroInfoEntityToDomainConverter
import com.example.searchmapuniversity.data.converter.OneWayConverter
import com.example.searchmapuniversity.data.local.MetroDao
import com.example.searchmapuniversity.data.network.MetroApi
import com.example.searchmapuniversity.domain.repository.MetroRepository
import com.example.searchmapuniversity.models.data.local.MetroInfoEntity
import com.example.searchmapuniversity.models.data.network.metro.LineDto
import com.example.searchmapuniversity.models.domain.metro.MetroInfo
import com.example.searchmapuniversity.utils.DATABASE_ERROR
import com.example.searchmapuniversity.utils.NO_INTERNET_CONNECTION
import com.example.searchmapuniversity.utils.Result
import com.example.searchmapuniversity.utils.SERVER_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MetroRepositoryImpl @Inject constructor(
    private val metroApi: MetroApi,
    private val metroDao: MetroDao,
    private val metroInfoEntityToDomainConverter: MetroInfoEntityToDomainConverter,
    private val metroInfoDtoToEntityConverter: OneWayConverter<LineDto, MetroInfoEntity>
) : MetroRepository {
    override fun getMetroInfo(fetchFromRemote: Boolean): Flow<Result<List<MetroInfo>>> = flow {
        emit(Result.Loading())
        try {
            val metroInfoEntity = metroDao.getMetroInfo()
            val isDbEmpty = metroInfoEntity.isEmpty()
            val loadFromCache = !isDbEmpty && !fetchFromRemote
            if (loadFromCache) {
                emit(Result.Success(metroInfoEntityToDomainConverter.convert(metroInfoEntity)))
                return@flow
            }

            val tmp2 = metroApi.getAllMetroInfo(MOSCOW_ID).lineDtos
            val metroInfoDtoToEntity = tmp2.map { metroInfoDtoToEntityConverter.convert(it) }
            metroDao.clearStations()
            metroDao.clearLines()
            val tmp3 = metroInfoDtoToEntity.map { it.lineInfo }
            metroDao.insertMetroLines(tmp3)
            metroInfoDtoToEntity.forEach {
                val tmp4 = it.stationEntities
                metroDao.insertMetroStations(tmp4)
            }

            println("METRO2: ${metroDao.getMetroInfo()}")
            val tmp = metroDao.getMetroInfo()
            val result = metroInfoEntityToDomainConverter.convert(tmp)
            emit(Result.Success(result))

        } catch (e: HttpException) {
            emit(Result.Error(SERVER_ERROR))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_CONNECTION))
        } catch (e: SQLiteException) {
            emit(Result.Error(DATABASE_ERROR))
        }
    }
}

private const val MOSCOW_ID = 1