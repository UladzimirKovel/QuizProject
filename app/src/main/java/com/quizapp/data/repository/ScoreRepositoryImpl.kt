package com.quizapp.data.repository

import com.quizapp.data.database.QuizDatabase
import com.quizapp.data.entities.ScoreEntity
import com.quizapp.domain.repository.ScoreRepository

class ScoreRepositoryImpl(
    private val userDatabase: QuizDatabase
): ScoreRepository {

    override suspend fun getAllScore(): List<ScoreEntity> {
        return userDatabase.scoreDao().getAllScores()
    }

    override suspend fun insertScore(score: ScoreEntity) {
        userDatabase.scoreDao().insertScore(score)
    }

    override suspend fun getUserScores(userId: String): List<ScoreEntity> {
        return userDatabase.scoreDao().getUserScores(userId)
    }

    override suspend fun deleteAllScore() {
        userDatabase.scoreDao().deleteAllScores()
    }

    override suspend fun deleteQuiz(quizId: Long) {
        userDatabase.scoreDao().deleteQuiz(quizId)
    }
}