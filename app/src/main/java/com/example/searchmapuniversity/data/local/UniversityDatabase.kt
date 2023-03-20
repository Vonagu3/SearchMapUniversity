package com.example.searchmapuniversity.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.searchmapuniversity.models.data.local.MetroLineEntity
import com.example.searchmapuniversity.models.data.local.MetroStationEntity
import com.example.searchmapuniversity.models.data.local.UniversityInfoEntity

@Database(
    entities = [UniversityInfoEntity::class, MetroLineEntity::class, MetroStationEntity::class],
    version = 1
)
abstract class UniversityDatabase: RoomDatabase() {
    abstract val universityDao: UniversityDao
    abstract val metroDao: MetroDao
}