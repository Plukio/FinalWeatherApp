package com.example.finalweatherapp.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey val id: Int = 0,
    val userUid: String,
    val time: String,
    val lat: String,
    val lon: String
)