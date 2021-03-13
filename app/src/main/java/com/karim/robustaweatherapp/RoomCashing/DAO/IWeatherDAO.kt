package com.karim.robustaweatherapp.RoomCashing.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.karim.robustaweatherapp.model.roomcashing.OfflineWeather

@Dao
interface IWeatherDAO {

    /**\
     * insert weather data to the offline data
     */
    @Insert
    fun addWeatherData(offlineWeather: OfflineWeather)

    /**
     * Get weather data
     *getting the all offline data
     * @return
     */
    @Query("SELECT * FROM OFFLINE_WEATHER")
    fun getWeatherData():List<OfflineWeather>
}