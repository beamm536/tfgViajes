package com.appclass.myapplication.data_api.api.explorarlugares

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapBoxApiStaticImagesExplorarLugares {

    @GET("styles/v1/mapbox/streets-v12/static/{lon},{lat},14,0/600x400")
    suspend fun getStaticMapImage(
        @Path("lon") longitude: Double,
        @Path("lat") latitude: Double,
        @Query("access_token") accessToken: String
    ): retrofit2.Response<okhttp3.ResponseBody>
}