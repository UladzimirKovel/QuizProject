package com.quizapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quizapp.data.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE _email = :email")
    fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE _user = :user")
    fun getUser(user: String): UserEntity?

    @Query("DELETE FROM users WHERE _user = :user")
    fun deleteUser(user: String)
}

