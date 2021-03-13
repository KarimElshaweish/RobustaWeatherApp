package com.karim.robustaweatherapp.Utils

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

/**
 * Location
 *
 * @property context
 * @constructor Create empty Location
 */
class Location(var context:Context) {
    /**
     * Check gps status
     *checking if the current gps is open
     */
    var locationManager: LocationManager? = null
    var GpsStatus = false
    fun checkGpsStatus() {
        locationManager = context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        GpsStatus = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!GpsStatus) {
            turnGPSOn()
        }
    }
    /**
     * Turn g p s on
     *  opening dialog to open the GPS
     */
    private fun turnGPSOn() {
        val intent =  Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent)
    }
}