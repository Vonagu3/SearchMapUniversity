package com.example.searchmapuniversity.data.converter

interface TwoWayConverter<F, T>: OneWayConverter<F, T> {
    fun reverse(to: T): F
}