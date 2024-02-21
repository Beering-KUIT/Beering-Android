package com.example.beering

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.beering.data.AccessTokenInterceptor
import com.example.beering.util.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BeeringApplication : Application(){

    companion object{
        const val DEV_URL = "https://api.beering.shop"
        const val PROD_URL = ""
        const val BASE_URL = DEV_URL
        const val SPF_TAG = "beering-preferences"

        lateinit var retrofit : Retrofit
        lateinit var mSharedPreferences : SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()

        // 디버깅 시에만 로깅 되도록
        val client: OkHttpClient = if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .addInterceptor(AccessTokenInterceptor()) // JWT 자동 헤더 전송
                .build()
        } else {
            OkHttpClient.Builder()
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .addInterceptor(AccessTokenInterceptor()) // JWT 자동 헤더 전송
                .build()
        }

        retrofit =  Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        mSharedPreferences = applicationContext.getSharedPreferences(SPF_TAG, Context.MODE_PRIVATE)
    }

    fun okHttpClient_header(header: String) : OkHttpClient {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(HeaderInterceptor(header))
        return builder.addInterceptor(logging).build()
    }
}