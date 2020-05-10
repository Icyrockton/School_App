package com.icyrockton.school_app.fragment.second_class

import com.icyrockton.school_app.fragment.second_class.detail.SecondClassDetailViewModel
import com.icyrockton.school_app.fragment.second_class.selected.SecondClassSelectedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val secondClassModule= module {

    single { SecondClassRepository(get()) }

    viewModel { SecondClassViewModel(get()) }
    viewModel { SecondClassDetailViewModel(get()) }
    viewModel { SecondClassSelectedViewModel(get()) }
}