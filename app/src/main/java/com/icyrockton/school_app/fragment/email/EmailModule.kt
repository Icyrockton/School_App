package com.icyrockton.school_app.fragment.email

import com.icyrockton.school_app.fragment.email.detail.CoilImageGetter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val emailModule= module {

    viewModel { EmailViewModel(get()) }
    single { EmailRepository(get()) }
}