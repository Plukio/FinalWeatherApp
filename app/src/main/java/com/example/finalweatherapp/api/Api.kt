package com.example.finalweatherapp.api

import com.example.finalweatherapp.model.cb.WeatherResult
import com.example.finalweatherapp.model.data.ResopnseRaw
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

private const val BASE_URL = "https://api.openweathermap.org/"

class Api (){
    private val retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create<WeatherApi>()
    }

    fun current(lati: String, longi: String, exclude: String, appid: String, cb: WeatherResult) {
        retrofit.openWeatherCurrent(lati, longi, exclude, appid).enqueue(object : Callback<ResopnseRaw> {
            override fun onResponse(
                call: Call<ResopnseRaw>,
                response: Response<ResopnseRaw>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccessCurrent(response.body()!!)
                } else {
                    cb.onDataFetchedFailed()
                }

            }

            override fun onFailure(call: Call<ResopnseRaw>, t: Throwable) {
                cb.onDataFetchedFailed()
            }
        })
    }


}

