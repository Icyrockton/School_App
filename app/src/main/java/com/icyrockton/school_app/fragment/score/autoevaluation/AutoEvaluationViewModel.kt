package com.icyrockton.school_app.fragment.score.autoevaluation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icyrockton.school_app.base.NetworkType
import com.icyrockton.school_app.base.WrapperResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AutoEvaluationViewModel(private val repository: AutoEvaluationRepository) : ViewModel() {

     var totalSize=0
     private val _evaluationCourses = MutableLiveData<WrapperResult<List<AutoEvaluationCourse>>>()
         val evaluationCourses: LiveData<WrapperResult<List<AutoEvaluationCourse>>> = _evaluationCourses


     private val _evaluationPostProgress = MutableLiveData<WrapperResult<Int>>()     //正在提交的....
         val evaluationPostProgress: LiveData<WrapperResult<Int>> = _evaluationPostProgress

    companion object{
        private const val TAG = "AutoEvaluationViewModel"
    }
    init {
        refresh()
    }

    private fun refresh(){
        viewModelScope.launch {
            _evaluationCourses.postValue(WrapperResult.loading)
            val allNeedEvaluationCourse = repository.getAllNeedEvaluationCourse()
            totalSize=allNeedEvaluationCourse.size
            _evaluationCourses.postValue(WrapperResult.done(allNeedEvaluationCourse))
        }
    }

    fun post(defaultScore: String, defaultContent: String) {
        _evaluationPostProgress.postValue(WrapperResult(0,NetworkType.LOADING))
        viewModelScope.launch {
            var count=0

            _evaluationCourses.value!!.data!!.forEach {
                val needEvaluationCourse =
                    repository.getNeedEvaluationCourse(it.sid, it.lid, defaultScore, defaultContent)
                val response=repository.postEvaluationCourse(needEvaluationCourse)
                count++
                if (count < totalSize)
                    _evaluationPostProgress.postValue(WrapperResult(count,NetworkType.LOADING))
                else
                    _evaluationPostProgress.postValue(WrapperResult(count,NetworkType.DONE))
            }
        }

    }
}