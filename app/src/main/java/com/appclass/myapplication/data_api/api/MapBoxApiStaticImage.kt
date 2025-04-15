package com.appclass.myapplication.data_api.api

import com.appclass.myapplication.data_api.model.GeocodingResponse
import okhttp3.ResponseBody
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * la api de StaticImages no es necesario crear una capa del modelo
 * pq como tal no obtenemos como respuesta el json, nos devuelve una img en formato REsponseBody
 */

interface MapBoxApiStaticImage {

    @GET("styles/v1/mapbox/streets-v11/static/{lon},{lat},{zoom}/{width}x{height}") //streets-v11 - styles
    suspend fun getStaticMap(
        @Path("lon") lon: Double,
        @Path("lat") lat: Double,
        @Path("zoom") zoom: Int,
        @Path("width") width: Int,
        @Path("height") height: Int,
        @Query("access_token") accessToken: String

    ): Response<ResponseBody>

}