package com.icyrockton.school_app.base

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes

data class ThemeWrapper(
    val themeName:String,//主题名字
    @StyleRes val styleID: Int,
    @ColorRes val colorPrimary: Int,
    @ColorRes val colorPrimaryDark: Int,
    @ColorRes val colorAccent: Int
)