package com.icyrockton.school_app.fragment.email

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val emailModule= module {

    viewModel { EmailViewModel(get()) }
    single { EmailRepository(get()) }
}