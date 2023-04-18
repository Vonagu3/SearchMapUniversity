package com.example.searchmapuniversity.presentation.recyclerview.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.searchmapuniversity.models.domain.diff.Payloadable
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.presentation.recyclerview.adapter.diff.BaseDiffCallback
import com.example.searchmapuniversity.presentation.recyclerview.adapter.holder.BaseViewHolder

/**
 *  Базовая реализация [ListAdapter]
 *
 *  @param Payload - payload для [BaseViewHolder]
 *  @param Model - model для [BaseViewHolder.bind]
 *  @param ViewHolder - наследник [BaseViewHolder]
 *  @constructor
 *  @param diffCallback - реализация [DiffUtil.ItemCallback] с учетом типа [Model]
 */
abstract class BaseListAdapter<Model: Payloadable, Payload: Any, ViewHolder: BaseViewHolder<Model, Payload>>(
    diffCallback: DiffUtil.ItemCallback<Model> = BaseDiffCallback()
) : ListAdapter<Model, ViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = getItem(position).let(holder::bind)

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) =
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads)
        } else{
            holder.update(getItem(position), payloads.lastOrNull() as? Payload)
        }
}