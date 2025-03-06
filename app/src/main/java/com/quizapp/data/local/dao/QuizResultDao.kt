package com.quizapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.quizapp.data.local.entity.QuizResultEntity

@Dao
interface QuizResultDao {
    @Query("SELECT * FROM quiz_results ORDER BY date DESC")
    fun getAllResults(): LiveData<List<QuizResultEntity>>

    @Insert
    suspend fun insertResult(result: QuizResultEntity)

    @Query("DELETE FROM quiz_results")
    suspend fun clearResults()
} 