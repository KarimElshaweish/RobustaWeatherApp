package com.karim.robustaweatherapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karim.robustaweatherapp.RoomCashing.Database.RebustaOfflineDatabase
import com.karim.robustaweatherapp.model.Weather.Weather
import com.karim.robustaweatherapp.model.Weather.WeatherData
import com.karim.robustaweatherapp.model.roomcashing.OfflineWeather
import com.karim.robustaweatherapp.repo.Repo
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Weather view model
 *
 * @property repo injected for Repo to pass value of the current api
 * @constructor Create empty Weather view model
 */
class WeatherViewModel @ViewModelInject constructor(private val repo:Repo):ViewModel(){
    var mutableLiveData=MutableLiveData<WeatherData>()

    fun getCurrentLocationWeather(appID:String,lat:String,lon:String)= getReponseFromApi(appID,lat,lon)


    /**
     * Get reponse from api
     *
     * getting the current weather information for API
     * @param appID current api ID
     * @param lat current location lat
     * @param lon current location lon
     */
    private fun getReponseFromApi(appID:String, lat:String, lon:String){
        repo.getWeatherFromAPI(appID,lat,lon).subscribeOn(Schedulers.io())
            .subscribe({
                result->mutableLiveData.postValue(result)
            },{
                error->Log.e("Error",error.message)
            })
    }



}