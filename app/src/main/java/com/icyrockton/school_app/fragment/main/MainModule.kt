package com.icyrockton.school_app.fragment.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainModule= module {
    single { MainRepository(get()) }
    viewModel { MainViewModel(get()) }

}