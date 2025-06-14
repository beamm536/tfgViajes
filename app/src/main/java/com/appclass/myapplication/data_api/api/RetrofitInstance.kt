package com.appclass.myapplication.data_api.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//configuracion de RETROFIT

object RetrofitInstance {
    private const val BASE_URL = "https://api.mapbox.com/"
    private const val MAPBOX_TOKEN = "pk.eyJ1Ijoid2F4ZXI1OSIsImEiOiJjbDMzZHJiN2cwdDA1M2pwOXlkbzVhb3kxIn0.lXwAZCDn_G9GNKcxWzYE7g"  //token de hugo :)

    /*
     - se crea un cliente HTTP (okhttp) >>> que no se crea hasta que alguien use la variable por primera vez
     EVITAMOS CARGA DE FUNCIONALIDADES AL ARRANCAR LA APP Y UN CRASHEO DE LA MISMA

        - añade logs para la depuracion con --> HttpLoggingInterceptor
        - añade la api-key como un parametro de la @Query
        - devuelve el cliente para ser usadon por retrofit
     */
    private val client by lazy{
        val logging = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder()
                    .addQueryParameter("access_token", MAPBOX_TOKEN)
                    .build()
                val request = original.newBuilder().url(url).build()
                chain.proceed(request)
            })
            .build()
    }


    val api: MapBoxApiGeocoding by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MapBoxApiGeocoding::class.java)
    }

    val apiStaticImage: MapBoxApiStaticImage by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            //.addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MapBoxApiStaticImage::class.java)
    }

}