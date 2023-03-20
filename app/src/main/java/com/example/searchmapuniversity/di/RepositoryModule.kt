package com.example.searchmapuniversity.di

import com.example.searchmapuniversity.data.converter.*
import com.example.searchmapuniversity.data.parser.UniversityParser
import com.example.searchmapuniversity.data.parser.XlsxParser
import com.example.searchmapuniversity.data.repository.MetroRepositoryImpl
import com.example.searchmapuniversity.data.repository.UniversityRepositoryImpl
import com.example.searchmapuniversity.domain.interactor.MetroInteractor
import com.example.searchmapuniversity.domain.interactor.MetroInteractorImpl
import com.example.searchmapuniversity.domain.interactor.UniversityInteractor
import com.example.searchmapuniversity.domain.interactor.UniversityInteractorImpl
import com.example.searchmapuniversity.domain.repository.MetroRepository
import com.example.searchmapuniversity.domain.repository.UniversityRepository
import com.example.searchmapuniversity.models.data.local.MetroInfoEntity
import com.example.searchmapuniversity.models.data.local.MetroLineEntity
import com.example.searchmapuniversity.models.data.local.MetroStationEntity
import com.example.searchmapuniversity.models.data.local.UniversityInfoEntity
import com.example.searchmapuniversity.models.data.network.metro.LineDto
import com.example.searchmapuniversity.models.data.network.yandex.FileItemDto
import com.example.searchmapuniversity.models.domain.metro.MetroInfo
import com.example.searchmapuniversity.models.domain.yandex.UniversityFileItem
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoList
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindUniversityParser(
        universityParser: UniversityParser
    ): XlsxParser<UniversityInfoList>

    @Binds
    @ViewModelScoped
    abstract fun bindUniversityInfoConverter(
        universityInfoConverter: UniversityInfoConverter
    ): TwoWayConverter<UniversityInfoEntity, UniversityInfoItem>

    @Binds
    @ViewModelScoped
    abstract fun bindUniversityFileItemConverter(
        universityFileItemConverter: UniversityFileItemConverter
    ): OneWayConverter<FileItemDto, UniversityFileItem>

    @Binds
    @ViewModelScoped
    abstract fun bindMetroInfoDtoToEntityConverter(
        metroInfoDtoToEntityConverter: MetroInfoDtoToEntityConverter
    ): OneWayConverter<LineDto, MetroInfoEntity>

//    @Binds
//    @ViewModelScoped
//    abstract fun bindMetroInfoEntityToDomainConverter(
//        metroInfoEntityToDomainConverter: MetroInfoEntityToDomainConverter
//    ): OneWayConverter<Map<MetroLineEntity, List<MetroStationEntity>>, List<MetroInfo>>

    @Binds
    @ViewModelScoped
    abstract fun bindUniversityRepository(
        universityRepositoryImpl: UniversityRepositoryImpl
    ): UniversityRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMetroRepository(
        metroRepositoryImpl: MetroRepositoryImpl
    ): MetroRepository

    @Binds
    @ViewModelScoped
    abstract fun bindUniversityInteractor(
        universityInteractorImpl: UniversityInteractorImpl
    ): UniversityInteractor

    @Binds
    @ViewModelScoped
    abstract fun bindMetroInteractor(
        metroInteractorImpl: MetroInteractorImpl
    ): MetroInteractor
}