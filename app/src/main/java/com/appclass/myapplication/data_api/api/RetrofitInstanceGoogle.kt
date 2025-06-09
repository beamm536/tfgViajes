package com.appclass.myapplication.data_api.api

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient


object RetrofitInstanceGoogle {
    private const val BASE_URL = "https://maps.googleapis.com/maps/api/"
    private const val GOOGLE_API_KEY = "AIzaSyBWNsg0IDYVrLyi3KokioyvmvCu6iH78AM" //apikey ---- pasar a $env (variable de entorno, para subir al gitignore)

    /*
     - se crea un cliente HTTP (okhttp) >>> que no se crea hasta que alguien use la variable por primera vez
     EVITAMOS CARGA DE FUNCIONALIDADES AL ARRANCAR LA APP Y UN CRASHEO DE LA MISMA ---> la instruccion se la proporciona "lazy"

        - añade logs para la depuracion con --> HttpLoggingInterceptor
        - añade la api-key como un parametro de la @Query
        - devuelve el cliente para ser usadon por retrofit
     */
    private val client by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
    }

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val originalUrl = original.url

                val url = originalUrl.newBuilder()
                    .addQueryParameter("key", GOOGLE_API_KEY)
                    .build()

                val request = original.newBuilder().url(url).build()
                chain.proceed(request)
            })
            .build()
    }

    val api: GooglePlacesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GooglePlacesApiService::class.java)
    }
}