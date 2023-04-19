package com.example.searchmapuniversity.presentation.account

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.searchmapuniversity.R
import com.example.searchmapuniversity.databinding.FragmentAccountBinding
import com.example.searchmapuniversity.presentation.account.adapter.LikedUniversitiesListAdapter
import com.example.searchmapuniversity.utils.Result
import com.example.searchmapuniversity.utils.UIEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {

    private lateinit var binding: FragmentAccountBinding
    private val viewModel: AccountViewModel by viewModels()
    private val adapter by lazy {
        LikedUniversitiesListAdapter {
            findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToFavouriteUniversitiesFragment(it))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountBinding.bind(view)
        binding.rvLikedUniversities.adapter = adapter
        observeError()
        viewModel.fetchLikedUniversities()
        viewModel.universitiesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    adapter.submitList(it.data)
                }
                else -> null
            }
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
}