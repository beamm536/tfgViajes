package com.appclass.myapplication.data_api.api

import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApiService {

    @GET("place/details/json")
    suspend fun getPlaceDetails (
        @Query("place_id") placeId: String,
        @Query("fields") fields: String = "name,formatted_address,photos,rating,editorial_summary,website",
        @Query("key") apiKey: String
    ): PlaceDetailsResponse
}