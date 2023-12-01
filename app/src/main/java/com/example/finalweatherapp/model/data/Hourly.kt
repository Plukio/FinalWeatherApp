package com.example.finalweatherapp.model.data

data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Long,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val uvi: Float,
    val visibility: Int,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double,
    val weather: List<WeatherX>,
    val pop: Double
)