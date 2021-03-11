package com.karim.robustaweatherapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.karim.robustaweatherapp.model.Weather.WeatherData
import com.karim.robustaweatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),View.OnClickListener {
    val REQUEST_CODE_FOR_LOCATION=77;
    var faceBookOperations:FaceBookOperations?=null
    var weatherViewModel:WeatherViewModel?=null

    val CAMERA_CODE_PERMISSON=101
    val CAMERA_CODE_REQUEST=102
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        faceBookOperations=FaceBookOperations(this,login_button,imageView,shareBtn)
        getTheCurrentLocation()
        weatherViewModel=ViewModelProvider(this).get(WeatherViewModel::class.java)
        takePhotoBtn.setOnClickListener(this)
       // printHashKey()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        faceBookOperations!!.onActivityResult(requestCode,resultCode,data)
            super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==CAMERA_CODE_REQUEST){
            val bitmap=data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
        }
        faceBookOperations?.shareImageContent(convertViewToBitmap(imageLayout))

    }
    private fun convertViewToBitmap(layout: RelativeLayout):Bitmap{
        layout.setDrawingCacheEnabled(true)
        val bitmap = Bitmap.createBitmap(layout.getDrawingCache())
        layout.setDrawingCacheEnabled(false)
        return bitmap
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
            if(grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                getLocation()
            }else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
            }
        }
        if(requestCode==CAMERA_CODE_PERMISSON&& grantResults.isNotEmpty()){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openCamera()
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
            cityName.text = data.name
            temp.text= data.main.temp.toString()
            humidity.text=data.main.humidity.toString()
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.takePhotoBtn->takePhotoFromCamera()
        }
    }

    private fun takePhotoFromCamera(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),CAMERA_CODE_PERMISSON)
        }else{
            openCamera()
        }
    }
    private fun openCamera(){
        val cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent,CAMERA_CODE_REQUEST)
    }

}