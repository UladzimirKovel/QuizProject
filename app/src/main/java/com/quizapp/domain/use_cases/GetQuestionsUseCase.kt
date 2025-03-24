package com.quizapp.domain.use_cases

import com.quizapp.data.model.Question
import com.quizapp.domain.exceptions.QuizExceptions
import com.quizapp.domain.repository.QuizRepository
import kotlin.jvm.Throws

class GetQuestionsUseCase(private val quizRepository: QuizRepository) {
    @Throws(QuizExceptions::class)
    suspend operator fun invoke(apiKey: String, category: String, difficulty: String, limit: Int): List<Question> {
        return quizRepository.getQuestion(apiKey, category, difficulty, limit)
    } // Экземпляр класс можно вызывать как функцию
}