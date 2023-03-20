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

class LikedUniversitiesListAdapter: ListAdapter<UniversityInfoItem, LikedUniversitiesListAdapter.UniversityViewHolder>(DiffCallback()) {

    class UniversityViewHolder(private val binding: LikedUniversityRvItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: UniversityInfoItem){
            binding.apply {
                Glide.with(binding.root.context).load(item.logo).circleCrop().into(imgvUniLogoLiked)
//                    .placeholder(ContextCompat.getDrawable(root.context, R.drawable.mgu))
                tvUniNameLiked.setText(Html.fromHtml(item.name, Html.FROM_HTML_MODE_LEGACY))
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<UniversityInfoItem>(){
        override fun areItemsTheSame(
            oldItem: UniversityInfoItem,
            newItem: UniversityInfoItem
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: UniversityInfoItem,
            newItem: UniversityInfoItem
        ) = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val binding = LikedUniversityRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UniversityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        val curItem = getItem(position)
        holder.bind(curItem)
    }
}