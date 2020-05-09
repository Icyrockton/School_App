package com.icyrockton.school_app.fragment.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icyrockton.school_app.base.WrapperResult
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository):ViewModel() {
    private var _scoreLiveData = MutableLiveData<WrapperResult<MainStuInfo>>()
    val scoreLiveData=_scoreLiveData
    init {
        scoreLiveData.postValue(WrapperResult.loading)
        refreshInfo()
    }

    fun refreshInfo(){
        viewModelScope.launch {
            scoreLiveData.postValue(WrapperResult.loading)
            val stuInfo = repository.getStuInfo()
            scoreLiveData.postValue(WrapperResult.done(stuInfo))
        }
    }
}