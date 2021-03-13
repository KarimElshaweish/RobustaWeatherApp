package com.karim.robustaweatherapp.RoomCashing.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karim.robustaweatherapp.RoomCashing.DAO.IWeatherDAO
import com.karim.robustaweatherapp.model.roomcashing.OfflineWeather

/**
 * Rebusta offline database
 *  with elites which hold the data and the current venison
 * @constructor Create empty Rebusta offline database
 */
@Database(entities = [OfflineWeather::class], version = 1)
abstract class RebustaOfflineDatabase : RoomDatabase(){
    /**
     * Companion
     *static fun and variables
     * getting instance of the current room cashing  instance
     * @constructor Create empty Companion
     */
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

    /**
     * I weather d a o
     *offline weather information data function interface
     * @return
     */
    abstract fun iWeatherDAO(): IWeatherDAO
}