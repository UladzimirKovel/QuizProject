package com.quizapp.di

import com.quizapp.data.database.QuizDatabase
import com.quizapp.data.repository.UserRepositoryImpl
import com.quizapp.domain.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {
single { QuizDatabase.getDatabase(get()) }
singleOf(::UserRepositoryImpl).bind(UserRepository::class)
}