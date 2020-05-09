package com.icyrockton.school_app.fragment.second_class

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SecondClassViewModel(private val repository: SecondClassRepository) : ViewModel() {
    private val _secondClassInfoLivedata = MutableLiveData<List<SecondClassWrapper>>()
    val secondClassInfoLivedata: LiveData<List<SecondClassWrapper>> = _secondClassInfoLivedata

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            _secondClassInfoLivedata.postValue(groupSecondClass())
            repository.getSecondClassDetailInfo("5D383F20D236665C","2")
        }
    }


    suspend fun groupSecondClass() = withContext(Dispatchers.IO) {
        val list = mutableListOf<SecondClassWrapper>()
        val groupBy = repository.getSecondClassInfo().groupBy {
            it.course_category
        }.forEach {
            list.add(SecondClassWrapper(it.key, it.value))
        }
        return@withContext list.toList()
    }
}