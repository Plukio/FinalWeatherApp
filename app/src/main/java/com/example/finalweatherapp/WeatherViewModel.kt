package com.example.finalweatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalweatherapp.api.Api
import com.example.finalweatherapp.model.cb.WeatherResult
import com.example.finalweatherapp.model.data.ResopnseRaw

const val TAG = "WeatherViewModel"

class WeatherViewModel : ViewModel(), WeatherResult {

    private val _currents = MutableLiveData<ResopnseRaw>()
    val currents: LiveData<ResopnseRaw> = _currents


    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val provider by lazy {
        Api()
    }

    fun current(lati: String, longi: String, exclude:String, appid:String) {
        provider.current(lati, longi, exclude, appid, this)
    }





    override fun onDataFetchedSuccessCurrent(weatherC: ResopnseRaw) {
        Log.d(TAG, "onDataFetchedSuccess | Received ${weatherC.lat} data")
        _currents.value = weatherC
    }



    override fun onDataFetchedFailed() {
        Log.e(TAG, "onDataFetchedFailed | Unable to retrieve images")
    }

}