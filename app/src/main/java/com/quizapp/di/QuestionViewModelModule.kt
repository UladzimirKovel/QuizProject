package com.quizapp.di

import com.quizapp.presentation.view_model.QuestionsViewModel
import org.koin.dsl.module

val questionViewModelModule = module {
    single { QuestionsViewModel(get()) }
}