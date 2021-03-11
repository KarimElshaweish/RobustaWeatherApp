package com.karim.robustaweatherapp.Utils

import android.content.Context
import android.content.SharedPreferences
import com.karim.robustaweatherapp.R

class MySharedPreferences(context:Context) {
    var sharedPref:SharedPreferences?=null
    val FACEBOOK_TAG="face_book_token";
    init {
        val sharedPref = context.getSharedPreferences(
            context.resources.getString(R.string.my_shared_references), Context.MODE_PRIVATE)
    }

    fun checkIfUserLoginWithFaceBook():Boolean{
        val value=sharedPref?.getString(FACEBOOK_TAG,"")
        return value!=""
    }
    fun saveFaceBookRegistration(token:String){
        with(sharedPref?.edit()){
            this?.putString(FACEBOOK_TAG,token)
            this?.apply()
        }
    }

    fun removeFaceBookToken(token: String) {
        with(sharedPref?.edit()){
            this?.remove(token)
            this?.apply()
        }
    }
}