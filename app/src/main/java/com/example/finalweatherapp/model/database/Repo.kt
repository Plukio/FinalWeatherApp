package com.example.finalweatherapp.model.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.finalweatherapp.model.data.Activity
import com.example.finalweatherapp.model.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class Repo(private val userdao: UserDao, private val activityDao: ActivityDao) {

    fun allUsers(): LiveData<List<User>> = userdao.allUsers()
    fun findUser(uid: String): LiveData<List<User>> = userdao.findUser(uid)
    fun addUser(user: User){
        UserDataActivityDatabase.databaseWriterExecutor.execute {
            userdao.addUser(user)
        }
    }

    fun addActivity(activity: Activity){
        UserDataActivityDatabase.databaseWriterExecutor.execute {
            activityDao.addActivity(activity)
        }
    }

}
