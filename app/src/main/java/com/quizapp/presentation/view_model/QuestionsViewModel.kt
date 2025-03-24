package com.quizapp.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.data.model.Question
import com.quizapp.domain.exceptions.QuizExceptions
import com.quizapp.domain.model.GameState
import com.quizapp.domain.use_cases.GetQuestionsUseCase
import kotlinx.coroutines.launch

class QuestionsViewModel(private val getQuestionsUseCase: GetQuestionsUseCase) : ViewModel() {
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
            _gameState.value = GameState.Loading
            score = 0
            currentQuestionIndex = 0

            try {
                //Вызываем use case
                questions = getQuestionsUseCase(apiKey, category, difficulty, limit)
                _gameState.value = GameState.Playing(
                    currentQuestion = questions[0],
                    currentIndex = 0,
                    totalQuestions = questions.size,
                    score = 0
                )
            } catch (e: QuizExceptions) {
                _gameState.value = GameState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun checkAnswer(selectedAnswer: String) {
        val currentState = _gameState.value
        // Проверяем, что игра находится в состоянии Playing
        if (currentState !is GameState.Playing) {
            return
        }
        // Получаем текущий вопрос
        val currentQuestion = questions[currentQuestionIndex]

        // Проверяем, является ли выбранный ответ правильным
        val correctAnswerValue = currentQuestion.correctAnswers?.get(selectedAnswer)

        val isCorrect = when (correctAnswerValue?.lowercase()) {
            "true" -> true
            else -> false
        }
        // Если ответ правильный, увеличиваем счет
        if (isCorrect) {
            score++
        }

        currentQuestionIndex++
        // Проверяем, есть ли еще вопросы
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