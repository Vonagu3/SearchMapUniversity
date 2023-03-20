package com.example.searchmapuniversity.models.data.local
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MetroLineEntity(
    @PrimaryKey
    @ColumnInfo(name = "line_id")
    val id: String,
    val name: String,
    val hexColor: String
)