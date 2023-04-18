package com.example.searchmapuniversity.models.domain.diff

interface Payloadable {
    fun calculatePayload(oldItem: Any?): Any?
}