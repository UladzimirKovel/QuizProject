package com.quizapp.di

import com.quizapp.data.api.QuizApi
import com.quizapp.presentation.view_model.QuestionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://quizapi.io/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApi::class.java)
    }

    val viewModelModule = module {
        viewModel { QuestionsViewModel(get()) } // Koin передает QuizApi в конструктор MyViewModel
    }
}