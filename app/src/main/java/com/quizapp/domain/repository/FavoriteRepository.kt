package com.quizapp.domain.repository

import com.quizapp.data.entities.FavoriteEntity

interface FavoriteRepository {
    suspend fun getFavoriteQuizzes(userId: String): List<FavoriteEntity>

    suspend fun addFavoriteQuiz(favoriteQuiz: FavoriteEntity)

    suspend fun deleteFavoriteQuiz(quizId: Long)

    suspend fun deleteAllFavoriteQuizzes(userId: String)
}