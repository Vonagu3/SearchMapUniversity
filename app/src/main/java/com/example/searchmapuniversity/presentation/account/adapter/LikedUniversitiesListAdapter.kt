package com.example.searchmapuniversity.presentation.account.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.searchmapuniversity.R
import com.example.searchmapuniversity.databinding.LikedUniversityRvItemBinding
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.models.domain.diff.UniversityListPayload
import com.example.searchmapuniversity.presentation.recyclerview.adapter.BaseListAdapter
import com.example.searchmapuniversity.presentation.recyclerview.adapter.holder.OnPayloadViewHolder

class LikedUniversitiesListAdapter(
    private val onClick: (item: UniversityInfoItem) -> Unit
) : BaseListAdapter<UniversityInfoItem, UniversityListPayload, LikedUniversitiesListAdapter.UniversityViewHolder>() {

    inner class UniversityViewHolder(
        private val binding: LikedUniversityRvItemBinding
    ) : OnPayloadViewHolder<UniversityInfoItem, UniversityListPayload>(binding.root) {

        override val defaulPayload = UniversityListPayload()

        override fun bind(model: UniversityInfoItem) {
            binding.apply {
                Glide.with(binding.root.context).load(model.logo).circleCrop()
                    .placeholder(
                        ContextCompat.getDrawable(
                            root.context,
                            R.drawable.baseline_image_not_supported_24
                        )
                    )
                    .into(imgvUniLogoLiked)
                tvUniNameLiked.text = Html.fromHtml(model.name, Html.FROM_HTML_MODE_LEGACY)
                itemView.setOnClickListener { onClick(model) }
            }
        }

        override fun bind(model: UniversityInfoItem, payload: UniversityListPayload) {
            if (payload.likeChanged)
                Glide.with(binding.root.context).load(model.logo).circleCrop()
                    .into(binding.imgvUniLogoLiked)
            else
                super.bind(model)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder =
        UniversityViewHolder(
            LikedUniversityRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}