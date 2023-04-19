package com.example.searchmapuniversity.models.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UniversityInfoEntity(
    @PrimaryKey
    val abbreviation: String,
    val name: String,
    val logo: String,
    val address: String,
    val lat: Double,
    val lon: Double,
    val site: String,
    val like: Int
)
