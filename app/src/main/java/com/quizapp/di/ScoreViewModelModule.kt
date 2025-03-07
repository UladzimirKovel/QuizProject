package com.quizapp.di

import com.quizapp.domain.repository.ScoreRepository
import com.quizapp.presentation.view_model.ScoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val scoreViewModelModule = module {
    viewModel { ScoreViewModel(get<ScoreRepository>()) }
}