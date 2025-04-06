package com.appclass.myapplication.data_api.model

import com.google.gson.annotations.SerializedName


//modelo de datos -- para lugar (ciudad) que vayamos a poner en nuestra aplicación
data class Feature(
    //val place_name: String,
    @SerializedName("place_name")
    val placeName: String,

    val geometry: Geometry, //no nos hace falta de momento

    val center: List<Double> //longitud y latitud del lugar buscado en nuestro buscador
    // val center: List<Double>?


    /**
     * Center -> es lo que vamos a usar directamente en la otra api que vamos a implementar
     * Static Images API -> https://docs.mapbox.com/api/maps/static-images/
     */
)
