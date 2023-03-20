package com.example.searchmapuniversity.di

import android.app.Application
import androidx.room.Room
import com.example.searchmapuniversity.data.converter.MetroInfoEntityToDomainConverter
import com.example.searchmapuniversity.data.converter.OneWayConverter
import com.example.searchmapuniversity.data.local.MetroDao
import com.example.searchmapuniversity.data.local.UniversityDao
import com.example.searchmapuniversity.data.local.UniversityDatabase
import com.example.searchmapuniversity.data.network.MetroApi
import com.example.searchmapuniversity.data.network.YandexCloudApi
import com.example.searchmapuniversity.models.data.local.MetroLineEntity
import com.example.searchmapuniversity.models.data.local.MetroStationEntity
import com.example.searchmapuniversity.models.domain.metro.MetroInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient.Builder()
        builder.networkInterceptors().add(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideMetroApi(okHttpClient: OkHttpClient): MetroApi{
        return Retrofit.Builder()
            .baseUrl(HH_METRO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MetroApi::class.java)
    }

    @Provides
    @Singleton
    fun provideYandexCloudApi(okHttpClient: OkHttpClient): YandexCloudApi {
        return Retrofit.Builder()
            .baseUrl(YANDEX_CLOUD_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YandexCloudApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUniversityDatabase(app: Application) =
        Room.databaseBuilder(app, UniversityDatabase::class.java, "university_db.db").build()

    @Provides
    @Singleton
    fun provideUniversityDao(universityDatabase: UniversityDatabase): UniversityDao =
        universityDatabase.universityDao

    @Provides
    @Singleton
    fun provideMetroDao(universityDatabase: UniversityDatabase): MetroDao =
        universityDatabase.metroDao

    @Provides
    @Singleton
    fun provideMetroInfoEntityToDomainConverter(): OneWayConverter<Map<MetroLineEntity, List<MetroStationEntity>>, List<MetroInfo>>{
        return MetroInfoEntityToDomainConverter()
    }
}

private const val YANDEX_CLOUD_BASE_URL = "https://cloud-api.yandex.net"
private const val HH_METRO_BASE_URL = "https://api.hh.ru"