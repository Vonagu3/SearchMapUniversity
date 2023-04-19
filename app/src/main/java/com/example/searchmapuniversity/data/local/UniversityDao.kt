package com.example.searchmapuniversity.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.searchmapuniversity.models.data.local.UniversityInfoEntity
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem

@Dao
interface UniversityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUniversityInfo(universityEntityList: List<UniversityInfoEntity>)

    @Query("DELETE FROM universityInfoEntity")
    suspend fun clearUniversityInfo()

    @Query("SELECT * FROM universityInfoEntity WHERE LOWER(abbreviation) LIKE LOWER(:query) || '%' ")
    suspend fun getUniversityList(query: String): List<UniversityInfoEntity>

    @Query("UPDATE universityInfoEntity SET like = :like WHERE name = :name")
    suspend fun likeUniversity(name: String, like: Int)

    @Query("SELECT * FROM universityInfoEntity WHERE like = 1")
    suspend fun getLikedUniversities(): List<UniversityInfoEntity>
}