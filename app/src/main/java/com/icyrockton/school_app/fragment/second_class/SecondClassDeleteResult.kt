package com.icyrockton.school_app.fragment.second_class

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SecondClassDeleteResult(
    @Json(name = "success")
    val success: String="",
    @Json(name = "flag")
    val flag: Boolean=false
)