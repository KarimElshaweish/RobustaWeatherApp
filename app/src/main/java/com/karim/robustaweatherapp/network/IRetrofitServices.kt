package com.karim.robustaweatherapp.network

import com.karim.robustaweatherapp.model.Weather.Weather
import com.karim.robustaweatherapp.model.Weather.WeatherData
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IRetrofitServices {

    @Headers("Accept: application/json")
    @GET(".")
    fun getCurrentLocationWeather(@Query("appid") appID:String,@Query("lat")lat:String,@Query("lon")lon:String):Observable<WeatherData>
}