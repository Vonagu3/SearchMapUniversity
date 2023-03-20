package com.example.searchmapuniversity.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmapuniversity.domain.interactor.MetroInteractor
import com.example.searchmapuniversity.models.domain.metro.MetroInfo
import com.example.searchmapuniversity.utils.Result
import com.example.searchmapuniversity.utils.UIEvent
import com.example.searchmapuniversity.utils.UNKNOWN_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapFilterViewModel @Inject constructor(
    private val metroInteractor: MetroInteractor
): ViewModel() {

    private val _metroInfoLiveData = MutableLiveData<Result<List<MetroInfo>>>()
    val metroInfoLiveData: LiveData<Result<List<MetroInfo>>> = _metroInfoLiveData

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun fetchMetroInfo(fetchFromRemote: Boolean){
        viewModelScope.launch {
            metroInteractor.fetchMetroInfo(fetchFromRemote).onEach {
                when(it){
                    is Result.Loading -> {
                        _metroInfoLiveData.postValue(Result.Loading())
                    }
                    is Result.Success -> {
                        _metroInfoLiveData.postValue(Result.Success(it.data))
                    }
                    is Result.Error -> {
                        _eventFlow.emit(UIEvent.ShowSnackbar(it.message ?: UNKNOWN_ERROR))
                    }
                }
            }.launchIn(this)
        }
    }
}