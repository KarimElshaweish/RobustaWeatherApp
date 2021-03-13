package com.karim.robustaweatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karim.robustaweatherapp.RoomCashing.Database.RebustaOfflineDatabase
import com.karim.robustaweatherapp.model.roomcashing.OfflineWeather

/**
 * Histroy view model
 * @constructor Create empty Histroy view model
 */
class HistroyViewModel:ViewModel() {
    var offlineMutableLiveData= MutableLiveData<List<OfflineWeather>>()
    fun getOfflineWeather(context: Context)=getOfflineDataFromRoomCashing(context)

    /**
     * Get offline data from room cashing
     *getting offline data form sqlite data which is saved using room cashing
     * using thread to reduce the time freezing the ui thread
     * posting value to live data
     * @param context
     */
    private fun getOfflineDataFromRoomCashing(context: Context){
        val robustaDatabase= RebustaOfflineDatabase.getInstance(context)
        val iWeatherDAO=robustaDatabase?.iWeatherDAO()
        Thread(Runnable {
            offlineMutableLiveData.postValue(iWeatherDAO?.getWeatherData())
        }).start()
    }
}