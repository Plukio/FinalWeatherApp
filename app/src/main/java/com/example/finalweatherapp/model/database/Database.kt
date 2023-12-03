package com.example.finalweatherapp.model.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalweatherapp.model.data.Activity
import com.example.finalweatherapp.model.data.User
import java.util.concurrent.Executors

@Database(
    entities = [User::class, Activity::class],
    version = 1,
    exportSchema = false
)

abstract class UserDataActivityDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun activityDao(): ActivityDao

    companion object {
        @Volatile
        private var INSTANCE: UserDataActivityDatabase? = null

        fun getDatabase(application: Application): UserDataActivityDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application,
                    UserDataActivityDatabase::class.java,
                    "UserDataActivityDatabase"
                )
                    .build()
                INSTANCE = instance
                instance
            }

        }
        val databaseWriterExecutor = Executors.newFixedThreadPool(2)
    }
}