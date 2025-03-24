package com.quizapp.di

import com.quizapp.data.repository.FavoriteRepositoryImpl
import com.quizapp.domain.repository.FavoriteRepository
import com.quizapp.presentation.adapter.FavoriteAdapter
import com.quizapp.presentation.view_model.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val favoriteModule = module {
    singleOf(::FavoriteRepositoryImpl).bind(FavoriteRepository::class)
    viewModel { FavoriteViewModel(get()) }
    factory {
        FavoriteAdapter { quiz ->
            get<FavoriteViewModel>().deleteFavoriteQuiz(
                quiz.id,
                quiz.userId
            )
        }
    }
}
//quiz -> ... - параметр лямбда-функции. Когда пользователь нажимает на кнопку удаления, адаптер передает в эту функцию объект FavoriteQuizEntity (который мы назвали quiz).
//get<FavoriteViewModel>() - получение экземпляра FavoriteQuizViewModel из контейнера Koin. Это позволяет нам получить доступ к ViewModel прямо из адаптера.
//.deleteFavoriteQuiz(quiz.id, quiz.userId) - вызов метода удаления квиза из избранного, передавая ID квиза и ID пользователя.