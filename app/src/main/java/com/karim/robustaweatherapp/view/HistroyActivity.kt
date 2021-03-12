package com.karim.robustaweatherapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.karim.robustaweatherapp.Adapter.WeatherDataAadpter
import com.karim.robustaweatherapp.IntroExplanision
import com.karim.robustaweatherapp.R
import com.karim.robustaweatherapp.viewmodel.HistroyViewModel
import com.karim.robustaweatherapp.viewmodel.WeatherViewModel
import dagger.hilt.EntryPoint
import kotlinx.android.synthetic.main.activity_histroy.*

class HistroyActivity : AppCompatActivity() {

    var historyViewModel:HistroyViewModel?=null
    var weatherDataAadpter:WeatherDataAadpter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_histroy)
        val introExplanision =IntroExplanision()
        introExplanision.createIntroForButton(addingFab,this)
        historyViewModel= ViewModelProvider(this).get(HistroyViewModel::class.java)
        setRVAdapter()
        getOfflineData()
        addingFab.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
    fun getOfflineData(){
        historyViewModel?.getOfflineWeather(this)
        historyViewModel?.offlineMutableLiveData?.observe(this, Observer {
                result-> runOnUiThread(Runnable {
            weatherDataAadpter?.setNewList(result)
        })
        })
    }

    fun setRVAdapter(){
        histroyRV.setHasFixedSize(true)
        histroyRV.layoutManager= GridLayoutManager(this,2)
        weatherDataAadpter= WeatherDataAadpter(emptyList())
        histroyRV.adapter=weatherDataAadpter
    }

    override fun onRestart() {
        super.onRestart()
        getOfflineData()
    }

    override fun onResume() {
        super.onResume()
        getOfflineData()
    }
}