package com.icyrockton.school_app.module

import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class NetWorkCookie:CookieJar {//保存Cookie
    companion object{
    private const val TAG = "NetWorkCookie"
}
    private var cookiesSave = mutableListOf<Cookie>() //保存的cookie
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookiesSave
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookiesSave.addAll(cookies)
    }
}