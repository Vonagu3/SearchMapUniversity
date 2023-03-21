package com.example.searchmapuniversity.presentation.recyclerview.adapter.holder

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmapuniversity.databinding.UniversityRvItemBinding

/**
 * Базовый [RecyclerView.ViewHolder] c [ViewBindingContainer]
 *
 * @param Model - тип model для связи с [itemView]
 * @param Payload - тип payload для частичного обновления [itemView] с помощью метода [bind]
 * @constructor
 * @param view - элемент представления для [RecyclerView.ViewHolder]
 */
abstract class BaseViewHolder<in Model: Any, in Payload: Any>(
    view: View
): RecyclerView.ViewHolder(view) {

    abstract fun bind(model: Model)

    abstract fun bind(model: Model, payload: Payload)

    open fun update(model: Model, payload: Payload?) {
        payload ?: return bind(model)
        bind(model, payload)
    }
}