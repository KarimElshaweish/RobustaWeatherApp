package com.karim.robustaweatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karim.robustaweatherapp.RoomCashing.Database.RebustaOfflineDatabase
import com.karim.robustaweatherapp.model.roomcashing.OfflineWeather

class HistroyViewModel:ViewModel() {
    var offlineMutableLiveData= MutableLiveData<List<OfflineWeather>>()
    fun getOfflineWeather(context: Context)=getOfflineDataFromRoomCashing(context)

    private fun getOfflineDataFromRoomCashing(context: Context){
        val robustaDatabase= RebustaOfflineDatabase.getInstance(context)
        val iWeatherDAO=robustaDatabase?.iWeatherDAO()
        Thread(Runnable {
            offlineMutableLiveData.postValue(iWeatherDAO?.getWeatherData())
        }).start()
    }
}