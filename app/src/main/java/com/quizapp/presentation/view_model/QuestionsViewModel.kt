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
    private val TAG = "QuestionsViewModel"

    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> get() = _gameState

    private var questions: List<Question> = emptyList()
    private var score = 0
    private var currentQuestionIndex = 0

    fun loadQuestions(category: String, difficulty: String, limit: Int) {
        Log.d(TAG, "Loading questions: category=$category, difficulty=$difficulty, limit=$limit")
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
                
                Log.d(TAG, "Received response from API")
                
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.isNotEmpty()) {
                        questions = responseBody
                        Log.d(TAG, "Received ${questions.size} questions")
                        
                        questions.forEachIndexed { index, question ->
                            Log.d(TAG, "=== Question $index ===")
                            Log.d(TAG, "Question text: ${question.question}")
                            Log.d(TAG, "Category: ${question.category}")
                            Log.d(TAG, "Difficulty: ${question.difficulty}")
                            Log.d(TAG, "Answers:")
                            question.answers?.forEach { (key, value) ->
                                Log.d(TAG, "  $key: '$value'")
                            }
                            Log.d(TAG, "Correct answer: '${question.correctAnswers}'")
                        }
                        
                        _gameState.value = GameState.Playing(
                            currentQuestion = questions[0],
                            currentIndex = 0,
                            totalQuestions = questions.size,
                            score = 0
                        )
                    } else {
                        Log.e(TAG, "Empty response body")
                        _gameState.value = GameState.Error
                    }
                } else {
                    Log.e(TAG, "API error: ${response.code()} - ${response.message()}")
                    _gameState.value = GameState.Error
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading questions", e)
                _gameState.value = GameState.Error
            }
        }
    }

    fun checkAnswer(selectedAnswer: String) {
        val currentState = _gameState.value
        if (currentState !is GameState.Playing) {
            Log.e(TAG, "Invalid game state for checking answer")
            return
        }

        val currentQuestion = questions[currentQuestionIndex]
        
        Log.d(TAG, "=== Answer Check Debug ===")
        Log.d(TAG, "Question ${currentQuestionIndex + 1}: ${currentQuestion.question}")
        Log.d(TAG, "Selected answer: '$selectedAnswer'")
        Log.d(TAG, "Correct answers: ${currentQuestion.correctAnswers}")
        
        // Проверяем, является ли выбранный ответ правильным
        val isCorrect = currentQuestion.correctAnswers?.get(selectedAnswer) == "true"
        
        if (isCorrect) {
            score++
            Log.d(TAG, "✓ Correct answer! New score: $score")
        } else {
            Log.d(TAG, "✗ Wrong answer. Selected: '$selectedAnswer'")
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
            Log.d(TAG, "Moving to next question ${currentQuestionIndex + 1}/${questions.size}")
        } else {
            Log.d(TAG, "=== Game Finished ===")
            Log.d(TAG, "Final score: $score/${questions.size}")
            _gameState.value = GameState.Finished(
                finalScore = score,
                totalQuestions = questions.size
            )
        }
    }

    fun getCurrentScore(): Int = score
}