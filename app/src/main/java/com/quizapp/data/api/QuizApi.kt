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

    @GET("categories")
    suspend fun getCategory(
        @Header("x-api-key") apiKey: String,
        @Query("category") category: String?
    ) : List<QuizResponse>
}