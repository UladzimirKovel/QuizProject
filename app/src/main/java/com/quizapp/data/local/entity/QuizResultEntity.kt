package com.quizapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String,
    val difficulty: String,
    val score: Int,
    val totalQuestions: Int,
    val date: Long = System.currentTimeMillis()
) 