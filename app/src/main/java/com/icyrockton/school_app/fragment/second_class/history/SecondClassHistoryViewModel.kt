package com.icyrockton.school_app.fragment.second_class.history


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icyrockton.school_app.base.WrapperResult
import com.icyrockton.school_app.fragment.second_class.SecondClassHistory
import com.icyrockton.school_app.fragment.second_class.SecondClassRepository
import kotlinx.coroutines.launch


class SecondClassHistoryViewModel(private val repository: SecondClassRepository) : ViewModel() {
     private val _historyLiveData = MutableLiveData<WrapperResult<List<SecondClassHistory>>>()
         val historyLiveData: LiveData<WrapperResult<List<SecondClassHistory>>> = _historyLiveData

    init {
        refreshData()
    }

     fun refreshData() {
        viewModelScope.launch {
            _historyLiveData.postValue(WrapperResult.loading)
            _historyLiveData.postValue(WrapperResult.done(repository.getHistoryInfo()))
        }
    }
}