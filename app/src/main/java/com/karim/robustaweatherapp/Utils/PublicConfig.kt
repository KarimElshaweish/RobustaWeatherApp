package com.karim.robustaweatherapp.Utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Public config
 *          This class used to get hashKey
 * @property context
 * @constructor Create empty Public config
 */
class PublicConfig(val context: Context) {
    fun printHashKey() {
        try {
            val info: PackageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
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

}