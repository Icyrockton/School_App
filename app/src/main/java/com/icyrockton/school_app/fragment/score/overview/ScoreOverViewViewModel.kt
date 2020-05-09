package com.icyrockton.school_app.fragment.score.overview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icyrockton.school_app.base.WrapperResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NumberFormatException

class ScoreOverViewViewModel(private val repository: ScoreOverViewRepository) : ViewModel() {

    companion object{
        private const val TAG = "ScoreOverViewViewModel"
    }
    private var _scoreLiveData = MutableLiveData<WrapperResult<ScoreWrapper>>()
    val scoreLiveData=_scoreLiveData
    init {
        scoreLiveData.postValue(WrapperResult.loading)
        refreshScoreListByDate(ScoreOverViewRepository.ORDER_BY_DESC)
    }

    fun refreshScoreList(orderType:String) {
        viewModelScope.launch {
            scoreLiveData.postValue(WrapperResult.loading)
            val score = repository.getAllScore(orderType)
            val scoreRatio = parseScoreRatio(score)
            scoreLiveData.postValue(WrapperResult.done(ScoreWrapper(ArrayList(score),scoreRatio)))
        }
    }
    fun refreshScoreListByDate(orderValue:String){
        viewModelScope.launch {
            scoreLiveData.postValue(WrapperResult.loading)
            val score = repository.getAllScoreByDate(orderValue)
            val scoreRatio = parseScoreRatio(score)
            scoreLiveData.postValue(WrapperResult.done(ScoreWrapper(ArrayList(score),scoreRatio)))
        }
    }

    suspend fun parseScoreRatio(score:List<ScoreDetail>):ScoreRatio= withContext(Dispatchers.IO){
        var excellent=0//90-100 优秀
        var good=0//80-90 良好
        var pass=0//60-80 及格
        var fail=0//0-60 不及格
        Log.d(TAG, "parseScoreRatio: ")
        score.forEach {
            try {
                val s = it.score.toFloat()
                when {
                    s>=90.0 -> excellent++
                    s>=80.0 -> good++
                    s>=60.0 -> pass++
                    else -> fail++
                }
            }catch (e:NumberFormatException){//通过 良 ?  报错
                good++
            }
        }
        return@withContext ScoreRatio(excellent, good, pass, fail)
    }
}