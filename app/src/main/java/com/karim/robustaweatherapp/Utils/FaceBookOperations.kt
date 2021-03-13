package com.karim.robustaweatherapp.Utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.facebook.share.model.ShareHashtag
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareButton
import com.karim.robustaweatherapp.R
import com.karim.robustaweatherapp.RoomCashing.RoomCashingOperation
import com.karim.robustaweatherapp.model.Weather.WeatherData

/**
 * Face book operations
 *this class is used to make operation on facebook api
 * @ini will init the data getting from facebook ,callback manager and token tracker to track the current facebook login state
 * @property context
 * @property loginButton
 * @property imageView
 * @property shareBtn
 * @constructor Create empty Face book operations
 */
class FaceBookOperations (var context: Context,var loginButton: LoginButton,var imageView: ImageView,var shareBtn: ShareButton) {

    var callbackManager:CallbackManager?=null
    val TAG="Login State"
    init {
        callbackManager= CallbackManager.Factory.create()
        loginButton.setPermissions(listOf("user_gender,user_friends"))
        loginButton.registerCallback(callbackManager,object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.d(TAG,"Login Successfully")
            }

            override fun onCancel() {
                Log.d(TAG,"Login canceld")
            }

            override fun onError(error: FacebookException?) {
                Log.d("Error",error?.message)
            }

        })
    //    checkIfUserLogin()
        val tokenTracker=object : AccessTokenTracker(){
            override fun onCurrentAccessTokenChanged(
                oldAccessToken: AccessToken?,
                currentAccessToken: AccessToken?
            ) {
                if(currentAccessToken==null){
                    shareBtn.shareContent=null
                 //   removeFaceBookLogin()
                }
            }

        }
    }

    /**
     * Share image content
     *used to set the sharedButton to share the image on facebook
     * @param bitmap getting image from camera passing from the @MainActivity
     * @param weatherData getting the weather information
     * @param imagepath getting image path of the sending image
     */
     fun shareImageContent( bitmap:Bitmap,weatherData: WeatherData,imagepath:String) {
        val sharePhoto = SharePhoto.Builder()
            .setBitmap(bitmap)
            .build()

        val sharePhotoContent = SharePhotoContent.Builder()
            .addPhoto(sharePhoto)
            .setShareHashtag(
                ShareHashtag.Builder()
                .setHashtag(context.getString(R.string.robusta)).build())
            .build()
        shareBtn.shareContent = sharePhotoContent
        shareBtn.setOnClickListener {
            saveToOffline(weatherData,imagepath)
        }
    }

    /**
     * Save to offline
     *              save information of the weather and image captured to the offline database
     * @param weatherData
     * @param imagePath
     */
    private fun saveToOffline(weatherData: WeatherData,imagePath: String) {
        val roomCashingOP=RoomCashingOperation(context)
        roomCashingOP.setDataToOfflineWeatherData(weatherData,imagePath)
    }

    /**
     * On activity result
     *                       overrid the @onActivityResult to pass parameters to the callback manager
     * @param requestCode
     * @param resultCode
     * @param data
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        callbackManager?.onActivityResult(requestCode,resultCode,data)
    }

}