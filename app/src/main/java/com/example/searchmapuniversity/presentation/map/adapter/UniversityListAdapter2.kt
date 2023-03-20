package com.example.searchmapuniversity.presentation.map.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.searchmapuniversity.databinding.UniversityRvItemBinding
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.presentation.recyclerview.adapter.BaseListAdapter
import com.example.searchmapuniversity.presentation.recyclerview.adapter.diff.BaseDiffCallback
import com.example.searchmapuniversity.presentation.recyclerview.adapter.holder.OffPayloadViewHolder

class UniversityListAdapter2(
    private val onClick: (item: UniversityInfoItem) -> Unit
): BaseListAdapter<UniversityInfoItem, Any, UniversityListAdapter2.UniversityViewHolder>(UniversityDiffCallback()) {

    inner class UniversityViewHolder(
        private val binding: UniversityRvItemBinding,
    ): OffPayloadViewHolder<UniversityInfoItem>(binding.root) {
        override fun bind(model: UniversityInfoItem) {
            binding.apply {
                Glide.with(root.context).load(model.logo).circleCrop().into(uniLogo)
                uniName.text = Html.fromHtml(model.name, Html.FROM_HTML_MODE_LEGACY)
                itemView.setOnClickListener { onClick(model) }
            }
        }
    }

    class UniversityDiffCallback: BaseDiffCallback<UniversityInfoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UniversityViewHolder(
            UniversityRvItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

}