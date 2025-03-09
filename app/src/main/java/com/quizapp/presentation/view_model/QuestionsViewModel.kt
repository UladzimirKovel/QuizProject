package com.quizapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.data.model.Question
import com.quizapp.data.repository.RetrofitClient
import com.quizapp.domain.model.GameState
import kotlinx.coroutines.launch

class QuestionsViewModel : ViewModel() {

    private val apiKey = "HaMStVN8pjYI5NysWMiSpBDTzprHNeBOE3ORm6sn"

    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> get() = _gameState

    private var questions: List<Question> = emptyList()
    private var score = 0
    private var currentQuestionIndex = 0
    private var currentCategory: String = ""
    private var currentDifficulty: String = ""

    fun loadQuestions(category: String, difficulty: String, limit: Int) {
        currentCategory = category
        currentDifficulty = difficulty
        viewModelScope.launch {
            try {
                _gameState.value = GameState.Loading
                score = 0
                currentQuestionIndex = 0

                val response = RetrofitClient.apiService.getQuestions(
                    apiKey = apiKey,
                    category = category,
                    difficulty = difficulty,
                    limit = limit
                )

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.isNotEmpty()) {
                        questions = responseBody
                        _gameState.value = GameState.Playing(
                            currentQuestion = questions[0],
                            currentIndex = 0,
                            totalQuestions = questions.size,
                            score = 0
                        )
                    } else {
                        _gameState.value = GameState.Error
                    }
                } else {
                    _gameState.value = GameState.Error
                }
            } catch (e: Exception) {
                _gameState.value = GameState.Error
            }
        }
    }

    fun checkAnswer(selectedAnswer: String) {
        val currentState = _gameState.value
        if (currentState !is GameState.Playing) {
            return
        }

        val currentQuestion = questions[currentQuestionIndex]

        // Проверяем, является ли выбранный ответ правильным
        val correctAnswerValue = currentQuestion.correctAnswers?.get(selectedAnswer)

        val isCorrect = when (correctAnswerValue?.lowercase()) {
            "true" -> true
            "1" -> true
            "yes" -> true
            else -> false
        }

        if (isCorrect) {
            score++
        } else {

        }

        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            val nextQuestion = questions[currentQuestionIndex]
            _gameState.value = GameState.Playing(
                currentQuestion = nextQuestion,
                currentIndex = currentQuestionIndex,
                totalQuestions = questions.size,
                score = score
            )
        } else {
            _gameState.value = GameState.Finished(
                finalScore = score,
                totalQuestions = questions.size,
                currentQuestion = currentQuestion
            )
        }
    }
}