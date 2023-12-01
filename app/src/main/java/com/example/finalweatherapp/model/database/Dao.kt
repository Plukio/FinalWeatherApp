package com.example.finalweatherapp.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.example.finalweatherapp.model.data.Activity
import com.example.finalweatherapp.model.data.User



@Dao
interface UserDao {

    @Insert
    fun addUser(user: User)

    @Query("SELECT * FROM users")
    fun allUsers(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE uid = :uid")
    fun findUser(uid: String): LiveData<List<User>>
}

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities")
    fun getAllActivities(): List<Activity>

    @Insert
    fun addActivity(activity: Activity)

}