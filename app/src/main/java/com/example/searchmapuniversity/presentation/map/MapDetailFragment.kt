package com.example.searchmapuniversity.presentation.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.searchmapuniversity.R
import com.example.searchmapuniversity.databinding.FragmentUniversityDetailBinding
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.utils.UIEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import com.example.searchmapuniversity.utils.Result
import com.example.searchmapuniversity.utils.UniFeedback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentUniversityDetailBinding
    private val args: MapDetailFragmentArgs by navArgs()
    private lateinit var universityInfoItem: UniversityInfoItem
    private val viewModel: MapDetailViewModel by viewModels()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUniversityDetailBinding.inflate(inflater, container, false)
        universityInfoItem = args.universityInfoItem
        observeError()
        observeLikedUniversity()

        binding.apply {
            Glide.with(root.context).load(universityInfoItem.logo).circleCrop().into(imgvUniLogo)
            tvUniName.setText(Html.fromHtml(universityInfoItem.name, Html.FROM_HTML_MODE_LEGACY))
            tvUniSite.text = universityInfoItem.site
            tvAddress.text = universityInfoItem.address
            imgvLikeUni.setImageDrawable(
                resources.getDrawable(
                    if (universityInfoItem.like == LIKED) R.drawable.baseline_thumb_up_24_red
                    else R.drawable.baseline_thumb_up_24
                )
            )
            imgvLocation.setOnClickListener {
                parentFragmentManager.setFragmentResult(
                    UNI_REQUEST_CODE,
                    bundleOf(UNI_SELECTED to universityInfoItem)
                )
            }
            imgvLikeUni.setOnClickListener {
                viewModel.likeUniversity(universityInfoItem)
            }
        }

        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeLikedUniversity() {
        viewModel.universityLiked.observe(viewLifecycleOwner) {
            binding.imgvLikeUni.setImageDrawable(
                when (it) {
                    is UniFeedback.LIKED -> {
                        resources.getDrawable(R.drawable.baseline_thumb_up_24_red)
                    }
                    is UniFeedback.UNLIKED -> {
                        resources.getDrawable(R.drawable.baseline_thumb_up_24)
                    }
                }
            )
        }
    }

    private fun observeError() {
        lifecycleScope.launchWhenCreated {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UIEvent.ShowSnackbar -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        const val UNI_REQUEST_CODE = "university_detail"
        const val UNI_SELECTED = "uni_selected"
        const val LIKED = 1
    }
}