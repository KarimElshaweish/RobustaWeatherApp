package com.karim.robustaweatherapp.Utils

import android.R
import android.app.Activity
import android.content.Context
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.facebook.share.widget.ShareButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig


/**
 * Intro explanision
 *          Used to make an intro explain every tool in the application
 * @constructor Create empty Intro explanision
 */
class IntroExplanision {
    fun createIntroForButton(mButtonShow:FloatingActionButton,activity: Activity){
        MaterialShowcaseView.Builder(activity)
            .setTarget(mButtonShow)
            .withButtonDismissStyle()
            .withWhiteDismissButton()
            .setDismissTextColor(ContextCompat.getColor(activity, R.color.darker_gray))
            .setDismissText("GOT IT")
            .setContentText("You can add new image with your location weather to the history ")
            .singleUse("1")
            .show()
    }
    fun createIntroMainActivity( activity: Activity,pickImageButton:Button){
        MaterialShowcaseView.Builder(activity)
            .setTarget(pickImageButton)
            .withButtonDismissStyle()
            .withWhiteDismissButton()
            .setDismissTextColor(ContextCompat.getColor(activity, R.color.darker_gray))
            .setDismissText("GOT IT")
            .setContentText("This is button to take image form camera and then share button will be enabled ")
              .singleUse("1")
            .show()

    }
}