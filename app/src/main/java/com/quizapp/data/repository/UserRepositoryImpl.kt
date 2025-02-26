package com.quizapp.data.repository

import com.quizapp.data.database.QuizDatabase
import com.quizapp.data.entities.UserEntity
import com.quizapp.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDatabase: QuizDatabase
) : UserRepository {
    override suspend fun registrationUser(user: String, email: String): Boolean {
        val existingUser = userDatabase.userDao().getUserByEmail( email)
        return if (existingUser == null) {
            val user = UserEntity(null, user = user, email = email)
            userDatabase.userDao().createUser(user)
            true
        } else false
    }

    override suspend fun loginUser(user: String): UserEntity? {
        return userDatabase.userDao().getUser(user)
    }

}