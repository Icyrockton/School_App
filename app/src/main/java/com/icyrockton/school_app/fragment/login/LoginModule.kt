package com.icyrockton.school_app.fragment.login

import com.icyrockton.school_app.fragment.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val LoginModule= module {

    viewModel { LoginViewModel(get()) }

    single { LoginRepository(get()) }
}