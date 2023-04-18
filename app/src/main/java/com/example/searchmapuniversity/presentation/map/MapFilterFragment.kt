package com.example.searchmapuniversity.presentation.map

import android.app.Dialog
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.searchmapuniversity.databinding.FragmentMapFilterBinding
import com.example.searchmapuniversity.models.domain.metro.FilterInfo
import com.example.searchmapuniversity.models.domain.metro.MetroFilter
import com.example.searchmapuniversity.models.domain.metro.MetroInfo
import com.example.searchmapuniversity.models.domain.metro.StationInfo
import com.example.searchmapuniversity.utils.Result
import com.example.searchmapuniversity.utils.UIEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MapFilterFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMapFilterBinding
    private val viewModel: MapFilterViewModel by viewModels()
    private var metroInfoList: List<MetroInfo>? = null
    private var metroStationList: List<StationInfo>? = null
    private lateinit var filterInfo: FilterInfo
    private val args: MapFilterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapFilterBinding.inflate(inflater, container, false)
        filterInfo =  args.filterInfo ?: FilterInfo()
        viewModel.fetchMetroInfo(true)
        observeError()
        observeMetroData()
        observeFilters()
        return binding.root
    }

    private fun observeFilters() {
        binding.apply {

            filterInfo.metroFilter?.metroRadius?.let {
                with(sliderMetroDistance) {
                    value = it
                    isEnabled = true
                }
            }

            btnApplyFilters.setOnClickListener {
                with(sliderMetroDistance){
                    if (isEnabled){
                        filterInfo.metroFilter?.metroRadius = value
                        parentFragmentManager.setFragmentResult(REQUEST_CODE, bundleOf(FILTER_INFO to filterInfo))
                        dismiss()
                    }
                }
            }

            btnResetFilters.setOnClickListener {
                parentFragmentManager.setFragmentResult(REQUEST_CODE, bundleOf(FILTER_INFO to null))
                dismiss()
            }
        }
    }

    private fun observeMetroData() {
        binding.apply {
            viewModel.metroInfoLiveData.observe(viewLifecycleOwner) {
                when(it) {
                    is Result.Success -> {
                        dropDownMetroLines.isEnabled = true
                        dropDownMetroStations.isEnabled = true
                        it.data?.let { metroInfoList ->
                            this@MapFilterFragment.metroInfoList = metroInfoList
                            val metroLines = metroInfoList.sortedBy { it.id }.map { it.name }
                            val lineAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, metroLines)
                            dropDownMetroLines.setAdapter(lineAdapter)

                            filterInfo.metroFilter?.linePosition?.let { pos ->
                                dropDownMetroLines.setText(metroLines[pos], false)
                                initDropdownStations(pos)
                            }
                        }
                    }
                    is Result.Loading -> {
                        dropDownMetroLines.isEnabled = false
                        dropDownMetroStations.isEnabled = false
                    }
                    else -> null
                }
            }
            dropDownMetroLines.setOnItemClickListener { _, _, position, _ ->
                initDropdownStations(position)
            }

            dropDownMetroStations.setOnItemClickListener { adapterView, view, position, id ->
                sliderMetroDistance.isEnabled = true
                with(filterInfo){
                    metroFilter = metroFilter?.apply {
                        stationPosition = position
                        lat = metroStationList?.get(position)?.lat
                        lng = metroStationList?.get(position)?.lng
                    }
                }
            }
        }
    }

    private fun initDropdownStations(position: Int) {
        with(filterInfo) {
            metroFilter = metroFilter?.apply { linePosition = position }
                ?: MetroFilter(linePosition = position)
        }

        metroInfoList?.let { metroInfoList ->
            metroInfoList
                .first { it.name == metroInfoList.sortedBy { it.id }[position].name }.stations
                .sortedBy { it.order }.let {
                    this@MapFilterFragment.metroStationList = it

                    val stationAdapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        it.map { it.name }
                    )
                    binding.dropDownMetroStations.setAdapter(stationAdapter)
                    filterInfo.metroFilter?.stationPosition?.let { pos ->
                        binding.dropDownMetroStations.setText(it[pos].name, false)
                    }
                }
        }
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

    companion object {
        const val REQUEST_CODE = "requestCode"
        const val FILTER_INFO = "filterInfo"
    }
}