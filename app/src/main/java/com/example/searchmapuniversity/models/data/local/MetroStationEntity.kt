package com.example.searchmapuniversity.models.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
//import androidx.room.ForeignKey
//import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.searchmapuniversity.models.data.network.metro.LineXDto

@Entity(
//    foreignKeys = [
//        ForeignKey(
//            entity = MetroLineEntity::class,
//            parentColumns = ["line_id"],
//            childColumns = ["line_id"],
//            onDelete = CASCADE,
//            onUpdate = CASCADE
//        )
//    ]
)
data class MetroStationEntity(
    @PrimaryKey
    val name: String,
    val lat: Double,
    val lng: Double,
    val order: Int,
    @ColumnInfo(name="line_id")
    val lineId: Int
)