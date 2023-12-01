package com.example.finalweatherapp.model.cb


import com.example.finalweatherapp.model.data.ResopnseRaw

interface WeatherResult {

    fun onDataFetchedSuccessCurrent(weatherC: ResopnseRaw)
    fun onDataFetchedFailed()


}