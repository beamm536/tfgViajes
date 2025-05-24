package com.appclass.myapplication.data_api.api_recomendacionXcoordenadas

data class PlaceRecomendaciones(
    val name: String,
    val placeId: String? = null,
    val rating: Double?,
    val reviews: Int?,
    val address: String?,
    val latitude: Double,
    val longitude: Double,
    val categories: List<String>,
    val photoUrl: String?
)
