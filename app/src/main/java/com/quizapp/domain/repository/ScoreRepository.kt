package com.quizapp.domain.repository

import com.quizapp.data.entities.ScoreEntity

interface ScoreRepository {

    suspend fun getAllScore(): List<ScoreEntity>

    suspend fun insertScore(score: ScoreEntity)

    suspend fun getUserScores(userId:String): List<ScoreEntity>

    suspend fun deleteAllScore()
}