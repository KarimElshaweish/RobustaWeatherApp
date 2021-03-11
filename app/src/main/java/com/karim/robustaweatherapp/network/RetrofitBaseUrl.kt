package com.karim.robustaweatherapp.network

import androidx.databinding.library.BuildConfig.DEBUG
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RetrofitBaseUrl {
   private val BASE_URL:String=""

   private val okHttpClient=OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .addInterceptor(loggingInterceptor())
        .readTimeout(90,TimeUnit.SECONDS)
        .build()

   private fun loggingInterceptor():Interceptor{
        val interceptor=HttpLoggingInterceptor()
        if(DEBUG){
            interceptor.level=HttpLoggingInterceptor.Level.BODY
        }else{
            interceptor.level=HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    private val gson=GsonBuilder().setLenient().create()

    private val retrofit=Retrofit.Builder().
            client(okHttpClient).baseUrl(BASE_URL).addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun creatServices()=retrofit.create(IRetrofitServices::class.java)
}