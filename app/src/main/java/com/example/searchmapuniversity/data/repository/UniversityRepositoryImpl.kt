package com.example.searchmapuniversity.data.repository
import android.database.sqlite.SQLiteException
import com.example.searchmapuniversity.BuildConfig.YANDEX_CLOUD_ACCESS_TOKEN
import com.example.searchmapuniversity.data.converter.OneWayConverter
import com.example.searchmapuniversity.data.converter.TwoWayConverter
import com.example.searchmapuniversity.data.local.UniversityDao
import com.example.searchmapuniversity.data.network.YandexCloudApi
import com.example.searchmapuniversity.data.parser.XlsxParser
import com.example.searchmapuniversity.domain.repository.UniversityRepository
import com.example.searchmapuniversity.models.data.local.UniversityInfoEntity
import com.example.searchmapuniversity.models.data.network.yandex.FileItemDto
import com.example.searchmapuniversity.models.domain.yandex.UniversityFileItem
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoList
import com.example.searchmapuniversity.utils.DATABASE_ERROR
import com.example.searchmapuniversity.utils.NO_INTERNET_CONNECTION
import com.example.searchmapuniversity.utils.Result
import com.example.searchmapuniversity.utils.SERVER_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.math.abs

class UniversityRepositoryImpl @Inject constructor(
    private val yandexCloudApi: YandexCloudApi,
    private val universityDao: UniversityDao,
    private val universityInfoConverter: TwoWayConverter<UniversityInfoEntity, UniversityInfoItem>,
    private val universityFileItemConverter: OneWayConverter<FileItemDto, UniversityFileItem>,
    private val universityXlsxParser: XlsxParser<UniversityInfoList>
): UniversityRepository {
    override fun getUniversities(fetchFromRemote: Boolean): Flow<Result<List<UniversityInfoItem>>> = flow {
        emit(Result.Loading())
        try {
            val localUniversityInfoList = universityDao.getUniversityList()
            val isDbEmpty = localUniversityInfoList.isEmpty()
            val loadFromCache = !isDbEmpty && !fetchFromRemote
            if (loadFromCache){
                emit(Result.Success(data = localUniversityInfoList.map { universityInfoConverter.convert(it) }))
                return@flow
            }

            val remoteUniversityFileInfo = yandexCloudApi.getAllFiles(token = YANDEX_CLOUD_ACCESS_TOKEN).fileItems
//                .first{ it.path.split("/")[1] == DIR_NAME }
                .filter { it.path.split("/")[1] == DIR_NAME }
                .map { universityFileItemConverter.convert(it) }
                .first()
            val universityFileByteStream = yandexCloudApi.downloadFile(fileUrl = remoteUniversityFileInfo.fileUrl).byteStream()
            val universityInfoList = universityXlsxParser.parse(stream = universityFileByteStream)

            universityDao.clearUniversityInfo()
            universityDao.insertUniversityInfo(
                universityEntityList = universityInfoList.universities.map {
                    universityInfoConverter.reverse(it)
                }
            )
            emit(Result.Success(data = universityDao.getUniversityList().map { universityInfoConverter.convert(it) }))

        } catch (e: HttpException){
            emit(Result.Error(SERVER_ERROR))
        }catch (e: IOException){
            emit(Result.Error(NO_INTERNET_CONNECTION))
        }catch (e: SQLiteException){
            emit(Result.Error(DATABASE_ERROR))
        }
    }

    override fun getLikedUniversities(): Flow<Result<List<UniversityInfoItem>>> = flow {
        emit(Result.Loading())
        try {
            val likedUniversities = universityDao.getLikedUniversities()
            emit(Result.Success(data = likedUniversities.map { universityInfoConverter.convert(it)}))
        }catch (e: SQLiteException){
            emit(Result.Error(DATABASE_ERROR))
        }
    }

//    override suspend fun likeUniversity(universityInfoItem: UniversityInfoItem): Result<Boolean> {
//        return try {
//            universityDao.likeUniversity(universityInfoItem.name, abs(1-universityInfoItem.like))
//            Result.Success(true)
//        }catch (e: SQLiteException){
//            Result.Error(DATABASE_ERROR)
//        }
//    }

    override fun likeUniversity(universityInfoItem: UniversityInfoItem): Flow<Result<Boolean>> = flow {
        try {
            universityDao.likeUniversity(universityInfoItem.name, abs(1-universityInfoItem.like))
            emit(Result.Success(true))
        }catch (e: SQLiteException){
            emit(Result.Error(DATABASE_ERROR))
        }
    }
}

private const val DIR_NAME = "Вузы"
