package com.icyrockton.school_app.fragment

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.icyrockton.school_app.fragment.login.LoginRepository
import com.icyrockton.school_app.fragment.login.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    companion object {
        private const val LOGIN_URL = "http://jwc.swjtu.edu.cn/service/login.html"

    }

    var userID: String = ""
    var userPassword: String = ""
    var verifyCode: String = ""
    fun login() = viewModelScope.launch {
        val result = repository.login(userID, userPassword, verifyCode)
        _loginResult.postValue(result)
    }

    init {

        requestJESSIONID()
    }

    fun requestJESSIONID() {//获取JESSION
        viewModelScope.launch {
            repository.getSESSIONID()
            refreshVerifyImage()
        }
    }

    private var _verifyImage = MutableLiveData<Bitmap>()
    val verifyImage = _verifyImage

    private var _loginResult = MutableLiveData<LoginResult>()
    val loginResult = _loginResult

    //刷新验证码
    fun refreshVerifyImage() = viewModelScope.launch {
        val image = repository.getVerifyImage(System.currentTimeMillis())
        _verifyImage.postValue(image)
    }

}