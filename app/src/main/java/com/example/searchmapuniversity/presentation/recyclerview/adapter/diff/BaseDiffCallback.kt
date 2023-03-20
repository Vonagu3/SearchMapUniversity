package com.example.searchmapuniversity.presentation.recyclerview.adapter.diff

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffCallback<T : Any>: DiffUtil.ItemCallback<T>() {

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem

}