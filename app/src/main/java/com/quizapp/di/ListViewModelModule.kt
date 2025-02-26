package com.quizapp.di

import com.quizapp.presentation.view_model.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val listViewModelModule = module {
    viewModelOf(::ListViewModel)
}