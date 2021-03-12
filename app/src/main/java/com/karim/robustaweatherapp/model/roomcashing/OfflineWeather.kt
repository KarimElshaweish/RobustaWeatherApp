package com.karim.robustaweatherapp.model.roomcashing

import com.karim.robustaweatherapp.model.Weather.WeatherData

data class OfflineWeather (
        val weatherData: WeatherData,
        val imagePath:String
)