package com.aap.weather.govweather.ui.main.network

import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val defaultUrl = "https://api.weather.gov"

    private var customUrlVar: String? = null

    fun setCustomUrl(url: String): RetrofitBuilder {
        customUrlVar = url
        return this
    }

    val url: String
        get() {
            customUrlVar?.apply {
                return this
            }
            return defaultUrl
        }

    private var _customConverterFactory: Converter.Factory? = null

    fun setCustomConverterFactory(converterFactory: Converter.Factory): RetrofitBuilder {
        _customConverterFactory = converterFactory
        return this
    }

    val converterFactory: Converter.Factory
        get() {
            _customConverterFactory?.apply {
                return this
            }
            return GsonConverterFactory.create()
        }


    fun build(): Retrofit {
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(converterFactory)
            .build()
    }
}