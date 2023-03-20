package com.example.searchmapuniversity.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.searchmapuniversity.models.data.local.MetroLineEntity
import com.example.searchmapuniversity.models.data.local.MetroStationEntity

@Dao
interface MetroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetroLines(metroLineEntities: List<MetroLineEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetroStations(metroStationEntities: List<MetroStationEntity>)

    @Query("SELECT * FROM metrolineentity ORDER BY line_id")
    suspend fun getLines(): List<MetroLineEntity>

    @Query("SELECT * FROM metrostationentity WHERE line_id = :lineId ORDER BY `order`")
    suspend fun getStations(lineId: Int): List<MetroStationEntity>

    @Query("SELECT l.*, s.* FROM metrolineentity as l JOIN metrostationentity as s ON l.line_id = s.line_id")
    suspend fun getMetroInfo(): Map<MetroLineEntity, List<MetroStationEntity>>

    @Query("DELETE FROM metrolineentity")
    suspend fun clearLines()

    @Query("DELETE FROM metrostationentity")
    suspend fun clearStations()
}