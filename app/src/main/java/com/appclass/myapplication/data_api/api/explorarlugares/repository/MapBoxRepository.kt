package com.appclass.myapplication.data_api.api.explorarlugares.repository

import com.appclass.myapplication.data_api.api.explorarlugares.MapBoxApiGeocodingExplorarLugares
import com.appclass.myapplication.data_api.model.GeocodingResponse

class MapBoxRepository (
    private val api: MapBoxApiGeocodingExplorarLugares
) {
    suspend fun buscarLugar(query: String, token: String): GeocodingResponse {
        return api.searchPlace(query, token)
    }
}