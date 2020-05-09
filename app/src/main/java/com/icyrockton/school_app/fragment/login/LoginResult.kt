package com.icyrockton.school_app.fragment.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResult(
    @Json(name = "loginMsg")
    val loginMsg: String,
    @Json(name = "loginStatus")
    val loginStatus: String)