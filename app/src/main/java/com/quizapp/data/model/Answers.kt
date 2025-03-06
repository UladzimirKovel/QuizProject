package com.quizapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answers(
    @SerializedName("answerA") val answerA: String,
    @SerializedName("answerB") val answerB: String,
    @SerializedName("answerC") val answerC: String,
    @SerializedName("answerD") val answerD: String
) : Parcelable