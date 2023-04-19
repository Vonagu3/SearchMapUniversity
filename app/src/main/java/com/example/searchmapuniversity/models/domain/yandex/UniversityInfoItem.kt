package com.example.searchmapuniversity.models.domain.yandex

import android.os.Parcelable
import com.example.searchmapuniversity.models.domain.diff.UniversityListPayload
import com.example.searchmapuniversity.models.domain.diff.Payloadable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UniversityInfoItem(
    val abbreviation: String,
    val name: String,
    val logo: String,
    val address: String,
    val lat: Double,
    val lon: Double,
    val site: String,
    val like: Int
): Parcelable, Payloadable {
    override fun calculatePayload(oldItem: Any?): UniversityListPayload? {
        if (oldItem !is UniversityInfoItem) return null

        return UniversityListPayload(
            likeChanged = oldItem.like != like
        )
    }

}