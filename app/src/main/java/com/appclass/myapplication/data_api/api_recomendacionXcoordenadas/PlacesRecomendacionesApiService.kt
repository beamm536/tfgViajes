package com.appclass.myapplication.data_api.api_recomendacionXcoordenadas

import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesRecomendacionesApiService {
    @GET("recommendations")
    suspend fun getRecommendations(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("type") type: String = "tourist_attraction"
    ): PlaceResponse

    //nuevo endpoint
    @GET("place-details")
    suspend fun getPlaceDetails(
        @Query("place_id") placeId: String
    ): DetallesResponse //esta data class se encuentra en: PlaceRecomendaciones
}