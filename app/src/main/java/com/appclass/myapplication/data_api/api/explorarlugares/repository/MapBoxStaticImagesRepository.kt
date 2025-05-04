package com.appclass.myapplication.data_api.api.explorarlugares.repository

import com.appclass.myapplication.data_api.api.explorarlugares.MapBoxApiStaticImagesExplorarLugares
import okhttp3.ResponseBody
import retrofit2.Response // jejeje te pilleee ya no me das el error del gms JEJEJ :v pinche bug

class MapBoxStaticImagesRepository (
    private val api: MapBoxApiStaticImagesExplorarLugares
) {
    suspend fun obtenerImagenMapa(longitud: Double, latitud: Double, token: String): Response<ResponseBody> {
        return api.getStaticMapImage(longitud, latitud, token)
    }
}