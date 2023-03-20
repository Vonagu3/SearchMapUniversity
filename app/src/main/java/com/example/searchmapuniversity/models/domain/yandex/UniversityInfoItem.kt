package com.example.searchmapuniversity.models.domain.yandex

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UniversityInfoItem(
    val name: String,
    val logo: String,
    val address: String,
    val lat: Double,
    val lon: Double,
    val site: String,
    val like: Int
): Parcelable