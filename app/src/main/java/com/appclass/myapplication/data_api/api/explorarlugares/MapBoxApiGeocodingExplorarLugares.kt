package com.appclass.myapplication.data_api.api.explorarlugares

import com.appclass.myapplication.data_api.model.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapBoxApiGeocodingExplorarLugares {
    @GET("geocoding/v5/mapbox.places/{search}.json")
    suspend fun searchPlace(
        @Path("search") search: String,
        @Query("access_token") accessToken: String,
        @Query("limit") limit: Int = 5,
        @Query("language") language: String = "es"
    ): GeocodingResponse
}