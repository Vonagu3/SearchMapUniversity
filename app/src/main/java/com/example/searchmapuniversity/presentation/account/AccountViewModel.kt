package com.example.searchmapuniversity.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmapuniversity.domain.interactor.UniversityInteractor
import com.example.searchmapuniversity.models.domain.yandex.UniversityInfoItem
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
class AccountViewModel @Inject constructor(
    private val universityInteractor: UniversityInteractor
): ViewModel() {

    private val _universitiesLiveData = MutableLiveData<Result<List<UniversityInfoItem>>>()
    val universitiesLiveData: LiveData<Result<List<UniversityInfoItem>>> =_universitiesLiveData

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun fetchLikedUniversities(){
        viewModelScope.launch {
            universityInteractor.fetchLikedUniversities().onEach {
                when(it){
                    is Result.Loading -> {
                        _universitiesLiveData.postValue(Result.Loading())
                    }
                    is Result.Success -> {
                        _universitiesLiveData.postValue(Result.Success(data = it.data))
                    }
                    is Result.Error -> {
                        _eventFlow.emit(UIEvent.ShowSnackbar(it.message ?: UNKNOWN_ERROR))
                    }
                }
            }.launchIn(this)
        }
    }
}