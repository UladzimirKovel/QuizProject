package com.quizapp.domain.model

import com.quizapp.data.model.Question

sealed class GameState {
    data object Loading : GameState()

    data class Playing(
        val currentQuestion: Question,
        val currentIndex: Int,
        val totalQuestions: Int,
        val score: Int
    ) : GameState()

    data class Finished(
        val finalScore: Int,
        val totalQuestions: Int,
        val currentQuestion: Question
    ) : GameState()

    data class Error(val message: String) : GameState()
}