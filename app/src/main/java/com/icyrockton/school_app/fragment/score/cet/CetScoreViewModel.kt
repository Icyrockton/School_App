package com.icyrockton.school_app.fragment.score.cet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icyrockton.school_app.base.WrapperResult
import kotlinx.coroutines.launch


class CetScoreViewModel(private val repository: CetScoreRepository) : ViewModel() {
    private var _scoreLiveData = MutableLiveData<WrapperResult<List<CetScore>>>()
    val scoreLiveData=_scoreLiveData
    init {
        scoreLiveData.postValue(WrapperResult.loading)
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            scoreLiveData.postValue(WrapperResult.loading)
            val result = repository.getCETScore()
            scoreLiveData.postValue(WrapperResult.done(result))
        }
    }
}