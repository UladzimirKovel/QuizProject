package com.quizapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.quizapp.data.entities.ScoreEntity

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores ORDER BY timestamp DESC")
    fun getAllScores(): List<ScoreEntity>

    @Insert
    suspend fun insertScore(scoreEntity: ScoreEntity)

    @Query("SELECT * FROM scores WHERE userId = :userId ORDER BY timestamp DESC")
    fun getUserScores(userId: String): List<ScoreEntity>

    @Query("DELETE FROM scores")
    suspend fun deleteAllScores()

    @Query("DELETE FROM scores WHERE id = :quizId")
    suspend fun deleteQuiz(quizId: Long)
}