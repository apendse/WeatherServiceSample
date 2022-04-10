package com.aap.weather.govweather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aap.weather.govweather.ui.main.data.PeriodDataVO
import com.aap.weather.govweather.ui.main.data.WeatherDataVO
import com.aap.weather.govweather.ui.main.network.RetrofitBuilder
import com.aap.weather.govweather.ui.main.network.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

const val lat = 37.4334
const val long = -121.9067
class MainViewModel : ViewModel() {
    private val _location = MutableLiveData<WeatherDataVO>()
    private val _periodData = MutableLiveData<List<PeriodDataVO>>()
    private val _error = MutableLiveData<String>()

    val error: LiveData<String> = _error


    private val retrofitBuilder: Retrofit by lazy {
        RetrofitBuilder.build()
    }

    fun fetchLocation(): LiveData<WeatherDataVO> {
        return _location
    }

    fun fetchHourlyWeather(): LiveData<List<PeriodDataVO>> {
        val weatherApi = retrofitBuilder.create(WeatherApi::class.java)
        viewModelScope.launch(Dispatchers.IO) {
            val resp = weatherApi.getWeatherData(lat.toString(), long.toString())
            if (resp.isSuccessful) {
                resp.body()?.let {
                    _location.postValue(it)
                    it.properties?.forecastHourly?.apply {
                        getHourlyWeather(weatherApi, this)
                    }  ?: _error.postValue("Empty response")

                }
            } else {
                _error.postValue("Error ${resp.code()}")
            }

        }
        return _periodData
    }

    private suspend fun getHourlyWeather(weatherApi: WeatherApi, url: String) {
        val resp = weatherApi.getHourly(url)
        if (resp.isSuccessful) {
            resp.body()?.properties?.periods?.apply {
                _periodData.postValue(this)
            }  ?: _error.postValue("Empty response 2")
        }
    }
}