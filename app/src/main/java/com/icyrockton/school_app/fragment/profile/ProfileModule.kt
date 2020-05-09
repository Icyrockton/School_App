package com.icyrockton.school_app.fragment.profile

import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule= module {
    viewModel { ProfileViewModel(get()) }
    single { ProfileRepository(get()) }

}