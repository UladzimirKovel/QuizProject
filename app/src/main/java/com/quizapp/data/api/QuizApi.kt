package com.quizapp.data.api

import com.quizapp.data.model.Question
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface QuizApi {

    @GET("questions")
    suspend fun getQuestions(
        @Header("x-api-key") apiKey: String,
        @Query("category") category: String,
        @Query("limit") limit: Int,
        @Query("difficulty") difficulty: String
    ): Response<List<Question>>
}