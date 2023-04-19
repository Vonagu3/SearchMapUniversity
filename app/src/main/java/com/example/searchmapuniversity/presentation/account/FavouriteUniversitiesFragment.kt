package com.example.searchmapuniversity.presentation.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.searchmapuniversity.R
import com.example.searchmapuniversity.databinding.FragmentFavouriteUniversitiesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteUniversitiesFragment: Fragment(R.layout.fragment_favourite_universities) {

    private lateinit var binding: FragmentFavouriteUniversitiesBinding
    private val args: FavouriteUniversitiesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouriteUniversitiesBinding.bind(view)
        binding.apply {
            with(args.universityInfoItem) {
                Glide.with(requireContext()).load(logo).circleCrop().into(imgvLogoDetail)
                tvAbbreviationDetail.text = abbreviation
                tvNameDetail.text = name
                tvAddressDetail.text = address
                tvWebsiteDetail.text = site
            }
        }
    }
}