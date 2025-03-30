package com.appclass.myapplication.data_api.model

import com.google.android.gms.common.Feature

//clase en la que se va a almacenar la respuesta que obtengamos de la api

data class GeocodingResponse (
    val features: List<Feature>
)
