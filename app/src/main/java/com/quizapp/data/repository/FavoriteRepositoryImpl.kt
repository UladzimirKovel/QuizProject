package com.quizapp.data.repository

import android.util.Log
import com.quizapp.data.database.QuizDatabase
import com.quizapp.data.entities.FavoriteEntity
import com.quizapp.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val database: QuizDatabase
) : FavoriteRepository {

    override suspend fun getFavoriteQuizzes(userId: String): List<FavoriteEntity> {
        return database.favoriteDao().getFavoriteQuizzes(userId)
    }

    override suspend fun addFavoriteQuiz(favoriteQuiz: FavoriteEntity) {
        database.favoriteDao().insertFavoriteQuiz(favoriteQuiz)
    }

    override suspend fun deleteFavoriteQuiz(quizId: Long) {
        database.favoriteDao().deleteFavoriteQuiz(quizId)
    }

    override suspend fun deleteAllFavoriteQuizzes(userId: String) {
        database.favoriteDao().deleteAllFavoriteQuizzes(userId)
    }
}