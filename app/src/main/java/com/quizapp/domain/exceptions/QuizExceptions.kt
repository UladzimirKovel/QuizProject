package com.quizapp.domain.exceptions

sealed class QuizExceptions(message: String): Exception(message) {
    class NetworkError(message: String): QuizExceptions(message)
    class ApiError(message: String): QuizExceptions(message)
    class EmptyResponseError(message: String): QuizExceptions(message)
}