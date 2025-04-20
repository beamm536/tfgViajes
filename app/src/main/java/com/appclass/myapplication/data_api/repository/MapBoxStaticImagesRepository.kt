package com.appclass.myapplication.data_api.repository

import com.appclass.myapplication.BuildConfig
import com.appclass.myapplication.data_api.api.MapBoxApiGeocoding
import com.appclass.myapplication.data_api.api.MapBoxApiStaticImage
import com.appclass.myapplication.data_api.model.GeocodingResponse
import okhttp3.ResponseBody
import retrofit2.Response

class MapBoxStaticImagesRepository (private val api: MapBoxApiStaticImage) {

    val accessToken = BuildConfig.MAPBOX_TOKEN
    //antes estaba privada :v - podemos cambiarlo ya que es un valor que no va a cambiar

    suspend fun getStaticMapImage(
        lon: Double,
        lat: Double,
        zoom: Int = 14,  // Valor por defecto
        width: Int = 600,
        height: Int = 400
    ): Response<ResponseBody> {


        return api.getStaticMap(lon, lat, zoom, width, height, accessToken)
    }
}