package com.appclass.myapplication.data_api.api

data class PlaceDetailsResponse (
    val result: PlaceResult? //null safety - para evitar error de NullPointerException
)