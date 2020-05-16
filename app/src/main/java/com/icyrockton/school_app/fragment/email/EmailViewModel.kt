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
import org.jsoup.Jsoup

class EmailViewModel(private val repository: EmailRepository) : ViewModel() {

     private val _inboxLiveData = MutableLiveData<WrapperResult<List<Email>>>()
         val inboxLiveData: LiveData<WrapperResult<List<Email>>> = _inboxLiveData
     private val _inboxCountLiveData = MutableLiveData<Int>() //未读数量
         val inboxCountLiveData: LiveData<Int> = _inboxCountLiveData
     private val _sendLiveData = MutableLiveData<WrapperResult<List<SendEmail>>>()
         val sendLiveData: LiveData<WrapperResult<List<SendEmail>>> = _sendLiveData
    private val _sendCountLiveData = MutableLiveData<Int>() //老师未读数量
    val sendCountLiveData: LiveData<Int> = _sendCountLiveData
     private val _imageUrlLiveData = MutableLiveData<List<String>>()
         val imageUrlLiveData: LiveData<List<String>> = _imageUrlLiveData

     private val _htmlLiveData = MutableLiveData<WrapperResult<String>>()
         val htmlLiveData: LiveData<WrapperResult<String>> = _htmlLiveData
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

    fun getEmailDetail(message_ID:String){
        viewModelScope.launch {
            _htmlLiveData.postValue(WrapperResult.loading)
            val htmlResponse = repository.getEmailDetail(message_ID)
            parseImageUrl(htmlResponse)
            _htmlLiveData.postValue(WrapperResult.done(htmlResponse))
        }
    }
    suspend fun parseImageUrl(htmlResponse: String) = withContext(Dispatchers.IO){
        val list= mutableListOf<String>()
        val allImage = Jsoup.parse(htmlResponse).select("img")
        allImage.forEach {
            list.add(it.attr("src"))
        }
        _imageUrlLiveData.postValue(list)
    }
    fun getImageUrl(index:Int):String?{
        return _imageUrlLiveData.value?.get(index)
    }
}