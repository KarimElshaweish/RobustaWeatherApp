package com.karim.robustaweatherapp.model.roomcashing

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.karim.robustaweatherapp.model.Weather.WeatherData

@Entity(tableName = "offline_weather")
data class OfflineWeather (
        @PrimaryKey(autoGenerate = true)
        val id:Int,
        val temp: String,
        val humidity:String,
        val place:String,
        val imagePath:String
)