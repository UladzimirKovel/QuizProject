package com.quizapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("question") val question: String,
    @SerializedName("description") val description : String?,
    @SerializedName("answer") val answer : Map<String,String?>,
    @SerializedName("multiple correct answers") val multiple_correct_answers : Boolean,
    @SerializedName("correct answers") val correct_answers : Map<String,Boolean>,
    @SerializedName("explanation") val explanation : String?,
    @SerializedName("tip") val tip : String?,
    @SerializedName("category") val category : String,
    @SerializedName("difficulty") val difficulty : String
) : Parcelable

@Parcelize
data class QuizGame(
    val question: String,
    val answers: Answers,
    val correctAnswer: String
) : Parcelable

@Parcelize
data class Answers(
    val answerA: String,
    val answerB: String,
    val answerC: String,
    val answerD: String
) : Parcelable

