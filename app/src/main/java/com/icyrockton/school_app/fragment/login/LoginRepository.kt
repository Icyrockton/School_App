package com.icyrockton.school_app.fragment.login

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.icyrockton.school_app.network.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val networkAPI: NetworkAPI) {
    suspend fun getSESSIONID() = withContext(Dispatchers.IO) {
        networkAPI.getLoginSESSION()
    }

    suspend fun getVerifyImage(currentTime: Long): Bitmap = withContext(Dispatchers.IO) {
        val byteStream = networkAPI.getVerifyCodeImage(currentTime).byteStream()
        BitmapFactory.decodeStream(byteStream)
    }

    suspend fun login(
        userID: String,
        userPassword: String,
        verifyCode: String
    ):LoginResult = withContext(Dispatchers.IO){
        val result = networkAPI.login(userID, userPassword, verifyCode)
        if (result.loginStatus.toInt() == 1){
            networkAPI.afterLogin()
        }
        result
    }
}