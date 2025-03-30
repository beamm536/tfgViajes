package com.appclass.myapplication.data_api.api

import com.appclass.myapplication.data_api.model.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//aqui vamos a definir los endpoints a los que vamos a acceder de la API

interface MapBoxApi {
    @GET("geocoding/v5/mapbox.places/{query}.json")
    suspend fun getGeocoding(
        @Path("query") query: String,
        //@Query("access_token") accessToken: String -- EL TOKEN YA SE LO HE PASADO A RETROFIT-INSTANCE
    ): GeocodingResponse

}