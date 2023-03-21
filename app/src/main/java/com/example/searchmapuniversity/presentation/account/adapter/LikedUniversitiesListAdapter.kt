package com.example.searchmapuniversity.presentation.account.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchmapuniversity.databinding.LikedUniversityRvItemBinding
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.presentation.recyclerview.adapter.BaseListAdapter
import com.example.searchmapuniversity.presentation.recyclerview.adapter.holder.OffPayloadViewHolder

class LikedUniversitiesListAdapter(
    private val onClick: (item: UniversityInfoItem) -> Unit
) : BaseListAdapter<UniversityInfoItem, Any, LikedUniversitiesListAdapter.UniversityViewHolder>() {

    inner class UniversityViewHolder(
        private val binding: LikedUniversityRvItemBinding
    ) : OffPayloadViewHolder<UniversityInfoItem>(binding.root) {
        override fun bind(model: UniversityInfoItem) {
            binding.apply {
                Glide.with(binding.root.context).load(model.logo).circleCrop()
                    .into(imgvUniLogoLiked)
//                    .placeholder(ContextCompat.getDrawable(root.context, R.drawable.mgu))
                tvUniNameLiked.setText(Html.fromHtml(model.name, Html.FROM_HTML_MODE_LEGACY))
                itemView.setOnClickListener { onClick(model) }
            }
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