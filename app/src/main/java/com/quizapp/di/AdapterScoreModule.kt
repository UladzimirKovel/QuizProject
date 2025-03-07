package com.quizapp.di

import com.quizapp.presentation.adapter.ScoreAdapter
import org.koin.dsl.module

val adapterScoreModule = module {
//    factory { ResultAdapter(arrayListOf()) }
    factory {ScoreAdapter()}
}