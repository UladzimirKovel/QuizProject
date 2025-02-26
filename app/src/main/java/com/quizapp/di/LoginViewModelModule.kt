package com.quizapp.di

import com.quizapp.presentation.view_model.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val loginViewModelModule = module {
    viewModelOf(::LoginViewModel)
}