package com.appclass.myapplication.data.recomendaciones

//modelo de datos - recomendaciones - campos en firestore
data class UserRecomendation(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val locationName: String = "", // "Barcelona, España"
    val lat: Double? = null,
    val lng: Double? = null,
    val category: String = "",
    val imageUrl: String = "",
    val userId: String = "",
    val isPublic: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)