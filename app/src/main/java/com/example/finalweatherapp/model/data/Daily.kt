package com.example.finalweatherapp.model.data

data class Daily(
    val dt: Long,
    val moon_phase: Double,
    val moonrise: Int,
    val moonset: Int,
    val summary: String,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temper,
    val weather: List<WeatherX>,

)