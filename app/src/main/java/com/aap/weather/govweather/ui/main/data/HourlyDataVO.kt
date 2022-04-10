package com.aap.weather.govweather.ui.main.data

data class HourlyDataVO(val properties: PropertiesVO?)

data class PropertiesVO(val periods:List<PeriodDataVO>?)

data class PeriodDataVO(val startTime: String?, val endTime: String?, val temperature: Int, val windDirection: String,
                         val icon: String?, val shortForecast: String?)