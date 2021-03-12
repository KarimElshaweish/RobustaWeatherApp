package com.karim.robustaweatherapp.RoomCashing

import android.content.Context
import com.karim.robustaweatherapp.RoomCashing.Database.RebustaOfflineDatabase
import com.karim.robustaweatherapp.model.Weather.WeatherData
import com.karim.robustaweatherapp.model.roomcashing.OfflineWeather

class RoomCashingOperation(var context: Context ) {
     fun setDataToOfflineWeatherData(weatherData: WeatherData,imagePath:String){
        val rebustaDatabase=RebustaOfflineDatabase.getInstance(context)
        val iWeatherDAO=rebustaDatabase?.iWeatherDAO()
        val offlineDatabase=OfflineWeather(0,weatherData.main.temp.toString(),weatherData.main.humidity.toString(),weatherData.name,imagePath)
        Thread(Runnable {
            iWeatherDAO?.addWeatherData(offlineDatabase)
        }).start()
    }
}