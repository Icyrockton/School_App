package com.icyrockton.school_app.fragment.score.daily

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icyrockton.school_app.base.WrapperResult
import com.icyrockton.school_app.fragment.score.NeedCourseEvaluationException
import kotlinx.coroutines.launch
import java.lang.Exception


class DailyScoreViewModel(private val repository: DailyScoreRepository) : ViewModel() {
    private var _scoreLiveData = MutableLiveData<WrapperResult<List<DailyScoreWrapper>>>()
    val scoreLiveData=_scoreLiveData
    init {
        scoreLiveData.postValue(WrapperResult.loading)
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            scoreLiveData.postValue(WrapperResult.loading)
            try {
                val result = repository.getDailyScore()
                scoreLiveData.postValue(WrapperResult.done(result))
            }catch (e:Exception){
                if (e is NeedCourseEvaluationException)
                    scoreLiveData.postValue(WrapperResult.error(e))
            }

        }
    }
}