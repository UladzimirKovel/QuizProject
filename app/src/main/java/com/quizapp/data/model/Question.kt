package com.quizapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    @SerializedName("id") val id: Int,
    @SerializedName("question") val question: String,
    @SerializedName("answers") val answers: Map<String, String?>?,
    @SerializedName("correct_answers") val correctAnswers: Map<String, String>?,
    @SerializedName("category") val category: String,
    @SerializedName("difficulty") val difficulty: String
) : Parcelable