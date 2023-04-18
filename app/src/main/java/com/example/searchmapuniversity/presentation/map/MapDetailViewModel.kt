package com.example.searchmapuniversity.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmapuniversity.domain.interactor.UniversityInteractor
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.searchmapuniversity.utils.Result
import com.example.searchmapuniversity.utils.UIEvent
import com.example.searchmapuniversity.utils.UNKNOWN_ERROR
import com.example.searchmapuniversity.utils.UniFeedback
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class MapDetailViewModel @Inject constructor(
    private val universityInteractor: UniversityInteractor
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _universityLiked = MutableLiveData<UniFeedback>()
    val universityLiked: LiveData<UniFeedback> = _universityLiked

    fun likeUniversity(universityInfoItem: UniversityInfoItem){
        viewModelScope.launch {
            universityInteractor.likeUniversity(universityInfoItem).onEach {
                when(it){
                    is Result.Success -> {
                        _universityLiked.postValue(it.data)
                    }
                    is Result.Error -> {
                        _eventFlow.emit(UIEvent.ShowSnackbar(it.message ?: UNKNOWN_ERROR))
                    }
                    else -> {}
                }
            }.launchIn(this)
//            withContext(Dispatchers.Default) {
//                val result = universityInteractor.likeUniversity(universityInfoItem)
//                when(result){
//                    is Result.Success -> {
//                        _universityLiked.postValue(Result.Success(true))
//                    }
//                    is Result.Error -> {
//                        _eventFlow.emit(UIEvent.ShowSnackbar(result.message ?: UNKNOWN_ERROR))
//                    }
//                    else -> {}
//                }
//            }
        }
    }
}