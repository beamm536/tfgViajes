package com.appclass.myapplication.ui.screens.mapbox.detalleMapa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class DetalleMapaViewModel : ViewModel() {

    // Aquí podrías hacer más lógica, como obtener info extendida del lugar
    var lugar by mutableStateOf<DetalleLugar?>(null)
        private set

    fun cargarLugar(nombre: String, lat: Double, lon: Double) {
        lugar = DetalleLugar(nombre, lat, lon)
    }
}

data class DetalleLugar(
    val nombre: String,
    val lat: Double,
    val lon: Double
)