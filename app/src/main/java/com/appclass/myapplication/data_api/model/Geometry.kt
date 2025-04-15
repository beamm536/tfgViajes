package com.appclass.myapplication.data_api.model


//modelo de datos para las coordenadas
data class Geometry(
    val coordinates: List<Double>,
    val type: String //puede q este vaya antes q coordinates, todo: cambiar si da error

    /**
     * type -> representa el tipo de geometria que estamos recibiendo
     */
)
