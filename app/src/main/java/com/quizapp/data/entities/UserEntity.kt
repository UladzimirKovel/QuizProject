package com.quizapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = 0,
    @ColumnInfo("_user") val user: String,
    @ColumnInfo("_email") val email: String,
)