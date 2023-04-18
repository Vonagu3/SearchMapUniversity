package com.example.searchmapuniversity.presentation.recyclerview.adapter.diff

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.searchmapuniversity.models.domain.diff.Payloadable

class BaseDiffCallback<T : Payloadable>: DiffUtil.ItemCallback<T>() {

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: T, newItem: T): Any? =
        newItem.calculatePayload(oldItem)

}