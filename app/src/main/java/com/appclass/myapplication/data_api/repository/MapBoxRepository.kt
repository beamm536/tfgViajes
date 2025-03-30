package com.appclass.myapplication.data_api.repository

import com.appclass.myapplication.data_api.api.MapBoxApi
import com.appclass.myapplication.data_api.model.GeocodingResponse


//repositorio para actuar con la api
class MapBoxRepository(private val api: MapBoxApi) {

    suspend fun getGeocoding(query: String): GeocodingResponse{
        return api.getGeocoding(query)
    }

}