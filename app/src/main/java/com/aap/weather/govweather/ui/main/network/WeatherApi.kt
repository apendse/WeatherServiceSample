package com.aap.weather.govweather.ui.main.network

import com.aap.weather.govweather.ui.main.data.HourlyDataVO
import com.aap.weather.govweather.ui.main.data.WeatherDataVO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface WeatherApi {
    //points/37.4334,-121.9067
    @GET("/points/{lat},{long}")
    suspend fun getWeatherData(@Path("lat") lat1: String, @Path("long") long1: String): Response<WeatherDataVO>

    @GET
    suspend fun getHourly(@Url url: String): Response<HourlyDataVO>


}