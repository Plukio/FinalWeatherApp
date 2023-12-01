package com.example.finalweatherapp.model.data

data class ResopnseRaw(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val hourly: List<Hourly>,
    val daily: List<Daily>
)