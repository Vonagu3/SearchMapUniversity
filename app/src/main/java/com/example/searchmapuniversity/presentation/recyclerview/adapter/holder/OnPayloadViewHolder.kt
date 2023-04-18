package com.example.searchmapuniversity.presentation.recyclerview.adapter.holder

import android.view.View

abstract class OnPayloadViewHolder<in Model: Any, Payload: Any>(
    view: View
): BaseViewHolder<Model, Payload>(view) {

    abstract val defaulPayload: Payload

    // зачем это? без этого нет bind с payload
    override fun bind(model: Model) = bind(model, defaulPayload)
}