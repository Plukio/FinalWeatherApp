package com.example.finalweatherapp

import android.app.Application
import com.example.finalweatherapp.model.database.Repo
import com.example.finalweatherapp.model.database.UserDataActivityDatabase

class AppApplication: Application() {

    val database by lazy {
        UserDataActivityDatabase.getDatabase(this)
    }
    val repository by lazy {
        Repo(database.userDao(), database.activityDao())
    }
}