package com.example.searchmapuniversity.utils

import android.view.View

sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(message: String, data: T? = null) : Result<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true) : Result<T>(null)
}

sealed class UniFeedback{
    object LIKED: UniFeedback()
    object UNLIKED: UniFeedback()
}

sealed class UIEvent{
    data class ShowSnackbar(val message: String): UIEvent()
}

interface ActionListener<in T> {
    fun onItemClicked(item: T, view: View? = null)
}

const val UNKNOWN_ERROR = "Неизвестная ошибка"