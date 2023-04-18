package com.example.searchmapuniversity.models.domain.metro

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterInfo(
    var subjectFilter: SubjectFilter? = null,
    var metroFilter: MetroFilter? = null,
    var scoreSum: Int? = null,
    var educationForm: EducationForm? = null
): Parcelable

@Parcelize
data class SubjectFilter(
    var isPhysics: Boolean,
    var isInformatics: Boolean,
): Parcelable

@Parcelize
data class MetroFilter(
    var stationPosition: Int? = null,
    var linePosition: Int? = null,
    var lat: Double? = null,
    var lng: Double? = null,
    var metroRadius: Float? = null
): Parcelable

@Parcelize
data class EducationForm(
    val isFullTime: Boolean,
    val isEvening: Boolean,
    val isPartTime: Boolean
): Parcelable
