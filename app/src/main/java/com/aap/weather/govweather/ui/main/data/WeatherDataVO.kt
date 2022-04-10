package com.aap.weather.govweather.ui.main.data

data class WeatherDataVO(val properties: WeatherPropertiesVO)


data class WeatherPropertiesVO(val forecast: String?, val forecastHourly: String?, val timeZone: String?,
                               val relativeLocation: RelativeLocationVO)

data class RelativeLocationVO(val type: String, val properties: LocationPropertiesVO)

data class LocationPropertiesVO(val city: String, val state: String)

