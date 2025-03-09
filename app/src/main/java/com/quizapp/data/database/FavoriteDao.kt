package com.quizapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.quizapp.data.entities.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_quizzes WHERE userId = :userId ORDER BY timestamp DESC")
    fun getFavoriteQuizzes(userId: String): List<FavoriteEntity>

    @Insert
    suspend fun insertFavoriteQuiz(favoriteQuiz: FavoriteEntity)

    @Query("DELETE FROM favorite_quizzes WHERE id = :quizId")
    suspend fun deleteFavoriteQuiz(quizId: Long)

    @Query("DELETE FROM favorite_quizzes WHERE userId = :userId")
    suspend fun deleteAllFavoriteQuizzes(userId: String)

}