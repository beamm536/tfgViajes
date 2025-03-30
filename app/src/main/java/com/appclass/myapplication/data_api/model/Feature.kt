package com.appclass.myapplication.data_api.model


//modelo de datos -- para lugar (ciudad) que vayamos a poner en nuestra aplicación
data class Feature(
    val placename: String,
    val geometry: Geometry
)
