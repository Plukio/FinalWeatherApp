package com.example.finalweatherapp.api

import com.example.finalweatherapp.model.data.ResopnseRaw
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val ACCESS_KEY = "6bee26772c654cecf15ac9e38d8e36cc"
const val EXCLUDE_FIELDS = "current,minutely,alerts"
interface WeatherApi {

    @GET("/data/3.0/onecall")
    fun openWeatherCurrent(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String = EXCLUDE_FIELDS,
        @Query("appid") appId: String = ACCESS_KEY
    ): Call<ResopnseRaw>

}
