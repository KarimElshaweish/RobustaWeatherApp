package com.karim.robustaweatherapp.repo

import com.karim.robustaweatherapp.model.Weather.Weather
import com.karim.robustaweatherapp.model.Weather.WeatherData
import com.karim.robustaweatherapp.network.IRetrofitServices
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class Repo @Inject constructor(val iRetrofitServices: IRetrofitServices) {
    fun getWeatherFromAPI(appID:String,lat:String,lon:String):Observable<WeatherData>
            = iRetrofitServices.getCurrentLocationWeather(appID,lat,lon)


}