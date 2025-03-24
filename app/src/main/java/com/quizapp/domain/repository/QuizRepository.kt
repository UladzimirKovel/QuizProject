package com.quizapp.domain.repository

import com.quizapp.data.model.Question
import com.quizapp.domain.exceptions.QuizExceptions
import kotlin.jvm.Throws

interface QuizRepository {
    @Throws(QuizExceptions::class)
    suspend fun getQuestion(
        apiKey: String,
        category: String,
        difficulty: String,
        limit: Int
    ): List<Question>
}