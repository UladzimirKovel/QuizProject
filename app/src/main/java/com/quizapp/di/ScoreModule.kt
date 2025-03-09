package com.quizapp.di

import com.quizapp.data.repository.ScoreRepositoryImpl
import com.quizapp.domain.repository.ScoreRepository
import com.quizapp.presentation.adapter.ScoreAdapter
import com.quizapp.presentation.view_model.ScoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val scoreModule = module {
    singleOf(::ScoreRepositoryImpl).bind(ScoreRepository::class)
    viewModel { ScoreViewModel(get()) }
    factory {
        ScoreAdapter { score ->
            get<ScoreViewModel>().deleteScoreById(
                score.id
            )
        }
    }
}