package com.karim.robustaweatherapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karim.robustaweatherapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Thread(Runnable {
            Thread.sleep(3000)
            startActivity(Intent(this,HistroyActivity::class.java))
        }).start()
    }
}