package com.pixelart.githubuserscoroutine.network

import com.pixelart.githubuserscoroutine.common.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {
    private fun interceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun okHttpClient(interceptor: HttpLoggingInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    fun networkService():NetworkService{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient(interceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}