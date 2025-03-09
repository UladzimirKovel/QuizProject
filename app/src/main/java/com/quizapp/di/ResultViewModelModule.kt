package com.quizapp.di

import com.quizapp.presentation.view_model.ResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val resultViewModelModule = module {
//    viewModelOf(::ResultViewModel)
    single { ResultViewModel(get ()) }
}