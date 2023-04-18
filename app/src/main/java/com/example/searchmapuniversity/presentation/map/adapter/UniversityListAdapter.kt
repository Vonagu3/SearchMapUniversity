package com.example.searchmapuniversity.presentation.map.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.searchmapuniversity.databinding.UniversityRvItemBinding
import com.example.searchmapuniversity.models.domain.diff.UniversityListPayload
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.presentation.recyclerview.adapter.BaseListAdapter
import com.example.searchmapuniversity.presentation.recyclerview.adapter.holder.OffPayloadViewHolder
import com.example.searchmapuniversity.presentation.recyclerview.adapter.holder.OnPayloadViewHolder

class UniversityListAdapter(
    private val onClick: (item: UniversityInfoItem) -> Unit
) : BaseListAdapter<UniversityInfoItem, UniversityListPayload, UniversityListAdapter.UniversityViewHolder>() {

    inner class UniversityViewHolder(
        private val binding: UniversityRvItemBinding,
    ) : OnPayloadViewHolder<UniversityInfoItem, UniversityListPayload>(binding.root) {

        override val defaulPayload = UniversityListPayload()

        override fun bind(model: UniversityInfoItem, payload: UniversityListPayload) {
            binding.apply {
//                if (!payload.likeChanged) {
                    Glide.with(root.context).load(model.logo).circleCrop().into(uniLogo)
                    uniName.text = Html.fromHtml(model.name, Html.FROM_HTML_MODE_LEGACY)
                    itemView.setOnClickListener { onClick(model) }
//                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UniversityViewHolder(
            UniversityRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

}