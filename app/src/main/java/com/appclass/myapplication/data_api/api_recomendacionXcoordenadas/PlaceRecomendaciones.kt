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


data class DetallesResponse(
    val name: String,
    val address: String?,
    val phone: String?,
    val website: String?,
    val openingHours: List<String>?,
    val reviews: List<Review>?,
    val photoUrl: String?,
    val summary: String?,
    val suggestions: List<Suggestion>?,
    val types: List<String>?
)