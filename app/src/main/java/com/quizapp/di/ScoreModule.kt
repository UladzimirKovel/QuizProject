package com.quizapp.di

import com.quizapp.data.database.QuizDatabase
import com.quizapp.data.repository.ScoreRepositoryImpl
import com.quizapp.domain.repository.ScoreRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val scoreModule = module {
    single { QuizDatabase.getDatabase(get()) }
    singleOf(::ScoreRepositoryImpl).bind(ScoreRepository::class)
}