package com.icyrockton.school_app.module

import com.icyrockton.school_app.base.SharedPreferencesHelper
import com.icyrockton.school_app.base.ThemeHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val HelperModule= module {
    single { SharedPreferencesHelper(androidContext()) }
    single { ThemeHelper(get(),get()) }
}