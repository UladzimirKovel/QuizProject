package com.quizapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.quizapp.data.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class QuizDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        fun getDatabase(context: Context): QuizDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                QuizDatabase::class.java,
                "QuizDB"
            ).build()
        }
    }
}