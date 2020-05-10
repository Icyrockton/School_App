package com.icyrockton.school_app.fragment.second_class.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icyrockton.school_app.base.WrapperResult
import com.icyrockton.school_app.fragment.second_class.SecondClassRepository
import com.icyrockton.school_app.fragment.second_class.SecondClassScoreInfo
import com.icyrockton.school_app.fragment.second_class.SecondClassTermInfo
import kotlinx.coroutines.launch

class SecondClassScoreQueryViewModel(private val repository: SecondClassRepository) : ViewModel() {
    private val _socreLiveData = MutableLiveData<WrapperResult<List<SecondClassScoreInfo>>>()
    val socreLiveData: LiveData<WrapperResult<List<SecondClassScoreInfo>>> = _socreLiveData
    private val _termInfoLiveData = MutableLiveData<List<SecondClassTermInfo>>()
    val termInfo: LiveData<List<SecondClassTermInfo>> = _termInfoLiveData

    init {
        getTermInfo()
        refreshData("所有", "-1")
    }

    fun getTermInfo() {
        viewModelScope.launch {
            _termInfoLiveData.value=repository.getTermInfo()
        }
    }

    fun refreshData(term_name:String,term_id:String) {
        viewModelScope.launch {
            _socreLiveData.postValue(WrapperResult.loading)
            val score = repository.getScore(
                term_name,
                term_id
            )
            val termInfo = repository.getTermInfo()
            score.forEach {
                it.semester= termInfo.find { term->  term.term_id==it.semester_ID }?.term_name.toString()
            }
            _socreLiveData.postValue(
                WrapperResult.done(
                    score
                )
            )
        }
    }
}