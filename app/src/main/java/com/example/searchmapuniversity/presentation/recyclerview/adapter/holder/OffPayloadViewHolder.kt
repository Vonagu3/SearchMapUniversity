package com.example.searchmapuniversity.presentation.recyclerview.adapter.holder

import android.view.View

abstract class OffPayloadViewHolder<in Model: Any>(
    view: View
): BaseViewHolder<Model, Any>(view) {
    override fun bind(model: Model, payload: Any) = bind(model)
}