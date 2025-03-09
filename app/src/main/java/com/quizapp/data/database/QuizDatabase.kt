package com.quizapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.quizapp.data.entities.FavoriteEntity
import com.quizapp.data.entities.ScoreEntity
import com.quizapp.data.entities.UserEntity

@Database(entities = [UserEntity::class, ScoreEntity::class, FavoriteEntity::class], version = 3)
abstract class QuizDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun scoreDao(): ScoreDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        fun getDatabase(context: Context): QuizDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                QuizDatabase::class.java,
                "QuizDB"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}