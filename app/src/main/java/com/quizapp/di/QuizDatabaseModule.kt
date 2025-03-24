package com.quizapp.di

import com.quizapp.data.database.QuizDatabase
import com.quizapp.data.repository.FavoriteRepositoryImpl
import com.quizapp.data.repository.ScoreRepositoryImpl
import com.quizapp.data.repository.UserRepositoryImpl
import com.quizapp.domain.repository.FavoriteRepository
import com.quizapp.domain.repository.ScoreRepository
import com.quizapp.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    // Предоставляем QuizDatabase
    single {
        QuizDatabase.getDatabase(androidContext())
    }

    // Регистрируем DAO
    single {
        get<QuizDatabase>().userDao()
    }

    single {
        get<QuizDatabase>().scoreDao()
    }

    single {
        get<QuizDatabase>().favoriteDao()
    }

    // Предоставляем репозитории
    single<FavoriteRepository> {
        FavoriteRepositoryImpl(get())
    }

    single<ScoreRepository>{
        ScoreRepositoryImpl(get())
    }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}