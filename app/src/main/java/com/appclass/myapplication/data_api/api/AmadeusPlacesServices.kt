package com.appclass.myapplication.data_api.api

import com.appclass.myapplication.data_api.model.amadeus.AmadeusPoisResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AmadeusPlacesServices {

    @GET("v1/reference-data/locations/pois")
    suspend fun getPointsOfInterest(
        @Header("Authorization") authHeader: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int = 5, // km
        @Query("categories") categories: String? = null, // opcional: "SIGHTS", "RESTAURANT"...
        @Query("page[limit]") limit: Int = 10
    ): AmadeusPoisResponse
}