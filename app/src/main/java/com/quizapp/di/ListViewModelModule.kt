package com.quizapp.di

import com.quizapp.presentation.view_model.ListViewModel
import org.koin.dsl.module

val listViewModelModule = module {
    single { ListViewModel() }
}