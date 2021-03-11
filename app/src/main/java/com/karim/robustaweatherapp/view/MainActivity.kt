package com.karim.robustaweatherapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.karim.robustaweatherapp.R
import com.karim.robustaweatherapp.Utils.FaceBookOperations
import com.karim.robustaweatherapp.model.Weather.Weather
import com.karim.robustaweatherapp.model.Weather.WeatherData
import com.karim.robustaweatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val REQUEST_CODE_FOR_LOCATION=77;
    var faceBookOperations:FaceBookOperations?=null
    var weatherViewModel:WeatherViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        faceBookOperations=FaceBookOperations(this,login_button,imageView,shareBtn)
        getTheCurrentLocation()
        weatherViewModel=ViewModelProvider(this).get(WeatherViewModel::class.java)
       // printHashKey()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        faceBookOperations!!.onActivityResult(requestCode,resultCode,data)
            super.onActivityResult(requestCode, resultCode, data)
        faceBookOperations?.shareImageContent()
    }

    fun printHashKey() {
        try {
            val info: PackageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("++++", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("+++++", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("++++", "printHashKey()", e)
        }
    }


    fun getTheCurrentLocation(){
        if(ContextCompat.checkSelfPermission(
                applicationContext,Manifest.permission.ACCESS_FINE_LOCATION
            )!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE_FOR_LOCATION
            )
        }else{
            getLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==REQUEST_CODE_FOR_LOCATION&& grantResults.isNotEmpty()){
            if(grantResults.get(0)==PackageManager.PERMISSION_GRANTED){
                getLocation()
            }else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLocation() {
        val locationRequest=LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval=3000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(locationRequest,object :LocationCallback(){
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    LocationServices.getFusedLocationProviderClient(this@MainActivity)
                        .removeLocationUpdates(this)
                    if(result.locations.size>0){
                        val index=result.locations.size-1
                        val lat= (result.locations[index].latitude).toString()
                        val lon= (result.locations[index].longitude).toString()
                        Log.d("lat: ",lat)
                        Log.d("lon: ",lon)
                        getWeatherData(lat,lon)
                    }
                }
            }, Looper.getMainLooper())

    }

    private fun getWeatherData(lat: String, lon: String) {
        weatherViewModel?.getCurrentLocationWeather("c48556f2022eda7bea9f8709f1f3d98a",lat,lon)
        weatherViewModel?.mutableLiveData?.observe(this,Observer{
            data:WeatherData-> println(data)
        })
    }

}