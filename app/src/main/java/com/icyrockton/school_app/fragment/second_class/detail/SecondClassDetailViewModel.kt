package com.icyrockton.school_app.fragment.second_class.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icyrockton.school_app.base.WrapperResult
import com.icyrockton.school_app.fragment.second_class.SecondClassDetailInfo
import com.icyrockton.school_app.fragment.second_class.SecondClassPostResult
import com.icyrockton.school_app.fragment.second_class.SecondClassRepository
import kotlinx.coroutines.launch

class SecondClassDetailViewModel(private val repository: SecondClassRepository) : ViewModel() {
     private val _detailInfo = MutableLiveData<WrapperResult<SecondClassDetailInfo>>()
         val detailInfo: LiveData<WrapperResult<SecondClassDetailInfo>> = _detailInfo
     private val _postResult = MutableLiveData<WrapperResult<SecondClassPostResult>>()
         val postResult: LiveData<WrapperResult<SecondClassPostResult>> = _postResult
    fun getData(ID:String,credit:String){
        viewModelScope.launch {

            _detailInfo.postValue(WrapperResult.loading)
            _detailInfo.postValue(WrapperResult.done(repository.getSecondClassDetailInfo(ID,credit)))
        }
    }
    fun post(ID:String){
        viewModelScope.launch {
            _postResult.postValue(WrapperResult.done(repository.postSecondClassAsAudience(ID)))
        }
    }
}