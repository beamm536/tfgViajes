package com.appclass.myapplication.data_api.api_recomendacionXcoordenadas

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {
    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.35:8000/")
        //.baseUrl("https://tfgbackend-hypb.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiService: PlacesRecomendacionesApiService = retrofit.create(
        PlacesRecomendacionesApiService::class.java
    )

}