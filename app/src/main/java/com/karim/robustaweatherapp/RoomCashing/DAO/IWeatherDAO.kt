package com.karim.robustaweatherapp.RoomCashing.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.karim.robustaweatherapp.model.roomcashing.OfflineWeather

@Dao
interface IWeatherDAO {

    @Insert
    fun addWeatherData(offlineWeather: OfflineWeather)

    @Query("SELECT * FROM OFFLINE_WEATHER")
    fun getWeatherData():List<OfflineWeather>
}