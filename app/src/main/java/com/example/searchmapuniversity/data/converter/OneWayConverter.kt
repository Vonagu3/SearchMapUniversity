package com.example.searchmapuniversity.data.converter

interface OneWayConverter<F, T> {
    fun convert(from: F): T
}