package com.karim.robustaweatherapp.Utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.widget.RelativeLayout
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ImageProcessing {
    /**
     * Convert view to bitmap
     *convert relativelayout to be an bitmap
     * @param layout of the image and banner of the current states
     * @return bitmap image
     */
    companion object {
        var mCurrentPhotoPath:String?=null
         fun convertViewToBitmap(layout: RelativeLayout): Bitmap {
            layout.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(layout.getDrawingCache())
            layout.isDrawingCacheEnabled = false
            return bitmap
        }
        /**
         * Create image file
         *  make an image path with current time in string value ending with .jpg
         * @return image path
         */
        fun createImageFile(context: Context): File {
            val timeStamp= SimpleDateFormat("yyyymmdd_hhmmss").format(Date())
            val imageName="jpg_"+timeStamp+"_"
            val storageDir=context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile= File.createTempFile(
                imageName,
                ".jpg",
                storageDir
            )
            mCurrentPhotoPath=imageFile.absolutePath
            return imageFile
        }
        fun getImagePath()= mCurrentPhotoPath
    }
}
