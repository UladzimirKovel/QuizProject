package com.quizapp.data.api

import com.quizapp.data.model.QuizResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface QuizApi {

    @GET("questions")
    suspend fun getAllQuestions(
        @Header("x-api-key") apiKey: String,
        @Query("category") category: String? = null,
    ): List<QuizResponse>

    @GET("questions")
    suspend fun getQuestions(
        @Query("category") category: String,
        @Query("limit") limit: Int,
        @Query("difficulty") difficulty: String,
        @Query("x-api-key") apiKey: String
    ): List<QuizResponse>
}