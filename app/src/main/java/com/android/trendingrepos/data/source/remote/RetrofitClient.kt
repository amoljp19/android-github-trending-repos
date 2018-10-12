package com.android.trendingrepos.data.source.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {

            val httpClient = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor { message -> Log.d("Retrofit", message) }
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)

            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build()
        }
        return retrofit!!
    }
}