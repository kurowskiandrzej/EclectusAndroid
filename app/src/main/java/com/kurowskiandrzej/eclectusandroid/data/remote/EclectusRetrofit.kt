package com.kurowskiandrzej.eclectusandroid.data.remote

import com.kurowskiandrzej.eclectusandroid.BuildConfig
import com.kurowskiandrzej.eclectusandroid.common.Constants
import com.kurowskiandrzej.eclectusandroid.data.remote.interceptor.EclectusInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EclectusRetrofit {
    private val loggingBodyInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val loggingHeadersInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.HEADERS
    }

    private val client = OkHttpClient.Builder().apply {
        this.addInterceptor(EclectusInterceptor())
        if (BuildConfig.DEBUG) {
            this.addInterceptor(loggingHeadersInterceptor)
                .addInterceptor(loggingBodyInterceptor)
        }
    }.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun get(): Retrofit {
        return retrofit
    }
}