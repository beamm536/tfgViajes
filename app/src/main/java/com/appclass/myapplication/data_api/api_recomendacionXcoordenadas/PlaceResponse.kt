package com.appclass.myapplication.data_api.api_recomendacionXcoordenadas

data class PlaceResponse(
    val recommendations: List<PlaceRecomendaciones>
)

data class Review(
    val authorName: String,
    val rating: Double,
    val text: String
)
