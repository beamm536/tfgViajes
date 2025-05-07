package com.appclass.myapplication.data_api.model.amadeus

data class AmadeusPoi(
    val id: String,
    val name: String?,
    val category: String,
    val geoCode: GeoCode, //data class declarada en model/amadeus/GeoCode con los atributos correspondientes
    val tags: List<String>?
)
