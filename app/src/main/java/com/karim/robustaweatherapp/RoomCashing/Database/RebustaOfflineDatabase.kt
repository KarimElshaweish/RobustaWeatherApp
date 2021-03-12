package com.karim.robustaweatherapp.RoomCashing.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karim.robustaweatherapp.RoomCashing.DAO.IWeatherDAO
import com.karim.robustaweatherapp.model.roomcashing.OfflineWeather

@Database(entities = [OfflineWeather::class], version = 1)
abstract class RebustaOfflineDatabase : RoomDatabase(){
        companion object {
            private val dbName = "rebousta_database"
            private var instance: RebustaOfflineDatabase? = null
            @Synchronized
            fun getInstance(context: Context?): RebustaOfflineDatabase? {
                if (instance == null)
                    instance =
                        Room.databaseBuilder(context!!, RebustaOfflineDatabase::class.java, dbName)
                            .fallbackToDestructiveMigration()
                            .build();

                return instance
            }
        }
    abstract fun iWeatherDAO(): IWeatherDAO
}