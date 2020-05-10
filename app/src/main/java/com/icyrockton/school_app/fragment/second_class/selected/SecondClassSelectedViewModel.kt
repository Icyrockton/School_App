package com.icyrockton.school_app.fragment.second_class.selected

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icyrockton.school_app.base.WrapperResult
import com.icyrockton.school_app.fragment.second_class.SecondClassDeleteResult
import com.icyrockton.school_app.fragment.second_class.SecondClassRepository
import com.icyrockton.school_app.fragment.second_class.SecondClassSelectedInfo
import kotlinx.coroutines.launch

class SecondClassSelectedViewModel(private val repository: SecondClassRepository) : ViewModel() {
     private val _selectedLiveData = MutableLiveData<WrapperResult<List<SecondClassSelectedInfo>>>()
         val selectedLiveData: LiveData<WrapperResult<List<SecondClassSelectedInfo>>> = _selectedLiveData
     private val _deleteLiveData = MutableLiveData<WrapperResult<SecondClassDeleteResult>>()
         val deleteLiveData: LiveData<WrapperResult<SecondClassDeleteResult>> = _deleteLiveData

    init {
        refreshData()

    }

    fun refreshData(){
        viewModelScope.launch {
            _selectedLiveData.postValue(WrapperResult.loading)
            _selectedLiveData.postValue(WrapperResult.done(repository.getSelectedClassInfo()))
        }
    }
    fun delete(deleteID:String){
        viewModelScope.launch {
            _deleteLiveData.postValue(WrapperResult.done(repository.deleteSecondClass(deleteID)))
        }
    }
}