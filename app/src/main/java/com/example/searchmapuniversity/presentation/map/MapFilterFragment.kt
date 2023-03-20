package com.example.searchmapuniversity.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.searchmapuniversity.databinding.FragmentMapFilterBinding
import com.example.searchmapuniversity.models.domain.metro.MetroInfo
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapFilterBinding.inflate(inflater, container, false)
        viewModel.fetchMetroInfo(true)
        observeError()
        observeMetroData()
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun observeMetroData(){
        binding.apply {
            viewModel.metroInfoLiveData.observe(viewLifecycleOwner){
                when(it){
                    is Result.Success -> {
                        it.data?.let { metroInfoList ->
                            this@MapFilterFragment.metroInfoList = metroInfoList
                            val metroLines = metroInfoList.sortedBy { it.id }.map { it.name }
                            val lineAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, metroLines)
                            dropDownMetroLines.setAdapter(lineAdapter)
                        }
                    }
                    is Result.Loading -> {
                        Toast.makeText(requireContext(), "Загрузка метро...", Toast.LENGTH_SHORT).show()
                    }
                    else -> null
                }
            }
            dropDownMetroLines.setOnItemClickListener { parent, view, position, id ->
                metroInfoList?.let { metroInfoList ->
                    val metroStations = metroInfoList
                        .first { it.name == metroInfoList.sortedBy { it.id }[position].name }.stations
                        .sortedBy { it.order }
                        .map { it.name }
                    val stationAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, metroStations)
                    dropDownMetroStations.setAdapter(stationAdapter)
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
}