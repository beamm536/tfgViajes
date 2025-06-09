package com.appclass.myapplication.data_api.repository

import com.appclass.myapplication.data_api.api.MapBoxApiGeocoding
import com.appclass.myapplication.data_api.model.GeocodingResponse
import com.appclass.myapplication.BuildConfig
import retrofit2.http.Query


//repositorio para actuar con la api
class MapBoxRepository(private val api: MapBoxApiGeocoding) {

    private val accessToken = BuildConfig.MAPBOX_TOKEN

    suspend fun getGeocoding(query: String): GeocodingResponse{
        return api.getGeocoding(
            query,
            accessToken,
            country = "es", //limitar la busqueda
            types = "place,address, poi"
        )
    }

}