package com.quizapp.di

import com.quizapp.data.repository.QuizRepositoryImpl
import com.quizapp.domain.repository.QuizRepository
import com.quizapp.domain.use_cases.GetQuestionsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val quizRepositoryModule = module {
    singleOf(::QuizRepositoryImpl).bind(QuizRepository::class)
    single { GetQuestionsUseCase(get()) }
}