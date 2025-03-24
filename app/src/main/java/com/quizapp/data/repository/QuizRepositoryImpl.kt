package com.quizapp.data.repository

import android.content.Context
import com.quizapp.data.api.QuizApi
import com.quizapp.data.model.Question
import com.quizapp.domain.exceptions.QuizExceptions
import com.quizapp.domain.repository.QuizRepository

class QuizRepositoryImpl(private val quizApi: QuizApi, context: Context) : QuizRepository {
    override suspend fun getQuestion(
        apiKey: String,
        category: String,
        difficulty: String,
        limit: Int
    ): List<Question> {
        return try {
            // Запрос к API
            val response = quizApi.getQuestions(apiKey, category, limit, difficulty)

            // Обработка ответа
            if (response.isSuccessful) {
                val question = response.body()
                if (question != null && question.isNotEmpty()) {
                    question // Успешный ответ
                } else {
                    throw QuizExceptions.EmptyResponseError("No questions found") // Пустой ответ
                }
            } else {
                throw QuizExceptions.ApiError("API request failed with code: ${response.code()}") // Ошибка API
            }
        } catch (e: Exception) {
            throw QuizExceptions.NetworkError("Network error: ${e.message}") // Ошибка сети или парсинга
        }
    }
}