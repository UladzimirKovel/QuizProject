package com.quizapp.di

import com.quizapp.data.repository.UserRepositoryImpl
import com.quizapp.presentation.view_model.LoginViewModel
import org.koin.dsl.module

val loginViewModelModule = module {
    single { UserRepositoryImpl(get()) }
    single {
        LoginViewModel(
            repository = get()
        )
    }
}