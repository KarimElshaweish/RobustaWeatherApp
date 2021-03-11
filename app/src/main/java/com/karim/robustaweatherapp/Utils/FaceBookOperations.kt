package com.karim.robustaweatherapp.Utils

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.facebook.share.model.ShareHashtag
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareButton
import com.karim.robustaweatherapp.R

class FaceBookOperations (var context: Context,var loginButton: LoginButton,var imageView: ImageView,var shareBtn: ShareButton) {

    var mySharedPreferences:MySharedPreferences?=null
    var callbackManager:CallbackManager?=null
    init {
        callbackManager= CallbackManager.Factory.create()
        mySharedPreferences= MySharedPreferences(context)
        loginButton.setPermissions(listOf("user_gender,user_friends"))
        loginButton.registerCallback(callbackManager,object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.d("demo","Login Successfully")
                saveFaceBookLogin()
            }

            override fun onCancel() {
                Log.d("demo","Login canceld")
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
    private fun removeFaceBookLogin() {
        mySharedPreferences!!.removeFaceBookToken("fb")
    }

    private fun checkIfUserLogin() {
        if(mySharedPreferences!!.checkIfUserLoginWithFaceBook())
            shareImageContent()
    }

    private fun saveFaceBookLogin() {
        mySharedPreferences!!.saveFaceBookRegistration("fb")
    }

     fun shareImageContent() {
        val bitmapDrawable: BitmapDrawable = imageView.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap

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
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        callbackManager?.onActivityResult(requestCode,resultCode,data)
    }

}