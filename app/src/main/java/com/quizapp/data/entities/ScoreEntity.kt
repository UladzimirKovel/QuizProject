package com.quizapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long
)