package com.appclass.myapplication.data_api.api


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AmadeusRetrofitInstance {

    private const val BASE_URL = "https://test.api.amadeus.com/"

    private val client by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    val authService: AmadeusAuthService by lazy {
        Retrofit.Builder()
            .baseUrl("https://test.api.amadeus.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AmadeusAuthService::class.java)
    }

    val placesServices: AmadeusPlacesServices by lazy {
        Retrofit.Builder()
            .baseUrl("https://test.api.amadeus.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AmadeusPlacesServices::class.java)
    }
}