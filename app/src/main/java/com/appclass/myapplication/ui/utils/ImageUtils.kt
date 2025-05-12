package com.appclass.myapplication.ui.utils

fun buildImageUrl(photoReference: String, apiKey: String): String {
    return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photoreference=$photoReference&key=$apiKey"
}