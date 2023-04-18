package com.example.searchmapuniversity.presentation.map

import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Property
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.searchmapuniversity.R
import com.example.searchmapuniversity.databinding.FragmentMapBinding
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import com.example.searchmapuniversity.presentation.map.MapDetailFragment.Companion.UNI_REQUEST_CODE
import com.example.searchmapuniversity.presentation.map.MapDetailFragment.Companion.UNI_SELECTED
import com.example.searchmapuniversity.presentation.map.adapter.UniversityListAdapter
import com.example.searchmapuniversity.utils.Result
import com.example.searchmapuniversity.utils.UIEvent
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MapFragment: Fragment(R.layout.fragment_map) {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: MapViewModel by viewModels()

    private val adapter by lazy {
        UniversityListAdapter{
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToMapDetailFragment(it))
        }
    }
    private var isUniversityRvVisible = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapKitFactory.initialize(context)
        binding = FragmentMapBinding.bind(view)

        binding.apply {
            rvContacts.adapter = adapter
            val itemAnimator = rvContacts.itemAnimator
            if (itemAnimator is DefaultItemAnimator){
                itemAnimator.supportsChangeAnimations = false
            }

            viewModel.fetchUniversities(fetchFromRemote = false)
            observeUniversitiesData()
            observeError()

            btnUniversityRvShrink.setOnClickListener {
                startRvAnimation()
            }

            swipeRefreshLayoutUniversities.setOnRefreshListener {
                viewModel.fetchUniversities(fetchFromRemote = true)
                swipeRefreshLayoutUniversities.isRefreshing = false
            }

            btnFilterUniversities.setOnClickListener {
                findNavController().navigate(MapFragmentDirections.actionMapFragmentToMapFilterFragment())
            }
        }
        parentFragmentManager.setFragmentResultListener(UNI_REQUEST_CODE, viewLifecycleOwner){ _, data ->
            val selected = data.get(UNI_SELECTED) as UniversityInfoItem
            zoomToUniversity(selected)
        }
    }

    private fun observeUniversitiesData(){
        viewModel.universitiesLiveData.observe(viewLifecycleOwner){
            when(it){
                is Result.Loading -> {
                    binding.progressBarUniversityLoading.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBarUniversityLoading.visibility = View.INVISIBLE
                    adapter.submitList(it.data)
                    it.data?.let { it1 -> initUniversitiesCoordinates(it1) }
                }
                else -> null
            }
        }
    }

    private fun initUniversitiesCoordinates(info: List<UniversityInfoItem>){
        binding.mapView.map.move(CameraPosition(Point(MOSCOW_LAT, MOSCOW_LON), 10.0f, 0.0f, 0.0f))
        val marker = ImageProvider.fromBitmap(getBitmapFromVectorDrawable(R.drawable.baseline_university))
        for (item in info){
            binding.mapView.map.mapObjects.addPlacemark(Point(item.lat, item.lon), marker)
        }
    }

    private fun zoomToUniversity(item: UniversityInfoItem){
        binding.mapView.map.move(CameraPosition(Point(item.lat, item.lon), 17.5f, 0.0f, 0.0f),
        Animation(Animation.Type.SMOOTH, 0f), null)
    }

    private fun getBitmapFromVectorDrawable(drawableId: Int): Bitmap? {
        var drawable = ContextCompat.getDrawable(requireContext(), drawableId)
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun observeError(){
        lifecycleScope.launchWhenCreated {
            viewModel.eventFlow.collectLatest { event ->
                when(event){
                    is UIEvent.ShowSnackbar -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun startRvAnimation(){
        binding.apply {
            val translationAnimationDelta =
                layoutUniversities.width.toFloat() - btnUniversityRvShrink.width.toFloat()
            if (isUniversityRvVisible) {
                animate(
                    target = layoutUniversities,
                    property = View.TRANSLATION_X,
                    from = 0f,
                    to = translationAnimationDelta,
                    duration = 1500
                )

                animate(
                    target = btnUniversityRvShrink,
                    property = View.ROTATION,
                    from = 0f,
                    to = 180f,
                    duration = 1000
                )
            }else{
                animate(
                    target = layoutUniversities,
                    property = View.TRANSLATION_X,
                    from = translationAnimationDelta,
                    to = 0f,
                    duration = 1500
                )
                animate(
                    target = btnUniversityRvShrink,
                    property = View.ROTATION,
                    from = 180f,
                    to = 0f,
                    duration = 1000
                )
            }
            isUniversityRvVisible = !isUniversityRvVisible
        }
    }

    private fun animate(
        target: View,
        property: Property<View, Float>,
        from: Float,
        to: Float,
        duration: Long
    ){
        val animation = ObjectAnimator.ofFloat(target, property, from, to)
        animation.duration = duration
        animation.start()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        binding.mapView.onStop()
    }
}

private const val MOSCOW_LAT = 55.75
private const val MOSCOW_LON = 37.62