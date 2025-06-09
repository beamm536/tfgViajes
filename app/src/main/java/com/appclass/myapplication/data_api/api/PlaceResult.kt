package com.appclass.myapplication.data_api.api

import com.mapbox.maps.extension.style.types.Formatted

//place result - es la clase en la que se definen los datos, los resultados que podemos obtener de la api (place-details)
data class PlaceResult (
    val name: String?,
    val formatted_address: String?,
    val rating: Double?,
    val photos: List<Photo>?,
    val website: String?,
    val editorial_summary: EditorialSummary? //editorialSummary es el objeto - overview >>> texto dentro del objeto

    /*
    RESPUESTA DE LA API - FORMATO JSON
    "editorial_summary": {
        "overview": "xxxxxxxxxxx"
    }
     */
)