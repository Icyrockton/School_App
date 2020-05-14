package com.icyrockton.school_app.fragment.email

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icyrockton.school_app.base.WrapperResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmailViewModel(private val repository: EmailRepository) : ViewModel() {

     private val _inboxLiveData = MutableLiveData<WrapperResult<List<Email>>>()
         val inboxLiveData: LiveData<WrapperResult<List<Email>>> = _inboxLiveData
     private val _inboxCountLiveData = MutableLiveData<Int>() //未读数量
         val inboxCountLiveData: LiveData<Int> = _inboxCountLiveData
     private val _sendLiveData = MutableLiveData<WrapperResult<List<SendEmail>>>()
         val sendLiveData: LiveData<WrapperResult<List<SendEmail>>> = _sendLiveData
    private val _sendCountLiveData = MutableLiveData<Int>() //老师未读数量
    val sendCountLiveData: LiveData<Int> = _sendCountLiveData
    companion object{
        private const val TAG = "EmailViewModel"
    }
    init {
        refreshInboxData()
        refreshSendData()
    }

    fun refreshSendData(){
        viewModelScope.launch {
            _sendLiveData.postValue(WrapperResult.loading)
            val sendEmail = repository.getSendEmail()
            var sumBy=0
            withContext(Dispatchers.IO){
                sumBy=sendEmail.sumBy { if (it.read)1 else 0 }
            }
            _sendLiveData.postValue(WrapperResult.done(sendEmail))
            _sendCountLiveData.postValue(sumBy)
        }
    }


    fun refreshInboxData(){
        viewModelScope.launch {
            _inboxLiveData.postValue(WrapperResult.loading)
            val allEmail = repository.getAllEmail()
            var sumBy=0
            withContext(Dispatchers.IO){
                sumBy= allEmail.sumBy { if (it.read) 1 else 0 }
            }
            _inboxLiveData.postValue(WrapperResult.done(allEmail))
            _inboxCountLiveData.postValue(sumBy)
        }
    }
}