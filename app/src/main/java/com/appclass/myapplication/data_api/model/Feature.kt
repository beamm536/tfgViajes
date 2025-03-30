package com.appclass.myapplication.data_api.model

import com.google.gson.annotations.SerializedName


//modelo de datos -- para lugar (ciudad) que vayamos a poner en nuestra aplicación
data class Feature(
    //val place_name: String,
    @SerializedName("place_name") val placeName: String,
    val geometry: Geometry
)
