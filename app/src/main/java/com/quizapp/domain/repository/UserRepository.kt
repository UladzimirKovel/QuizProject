package com.quizapp.domain.repository

import com.quizapp.data.entities.UserEntity

interface UserRepository {
    suspend fun registrationUser(user: String, email: String): Boolean?

    suspend fun loginUser(user: String): UserEntity?

}