package com.appclass.myapplication.ui.screens.mapbox.explorarLugares

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appclass.myapplication.data_api.api.explorarlugares.repository.MapBoxRepository
import com.appclass.myapplication.data_api.model.GeocodingResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapBoxViewModel(
    private val repository: MapBoxRepository
) : ViewModel() {

    // 🧠 Campo de texto del buscador
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun actualizarQuery(nuevaQuery: String) {
        _query.value = nuevaQuery
    }

    // 📍 Resultado del geocoding
    private val _resultadoGeocoding = MutableStateFlow<GeocodingResponse?>(null)
    val resultadoGeocoding: StateFlow<GeocodingResponse?> = _resultadoGeocoding

    // 🗺️ URL del mapa estático
    private val _urlMapa = MutableStateFlow<String?>(null)
    val urlMapa: StateFlow<String?> = _urlMapa

    // 🔍 Función para hacer la búsqueda
    fun buscarUbicacion() {
        viewModelScope.launch {
            try {
                val token = "pk.eyJ1Ijoid2F4ZXI1OSIsImEiOiJjbDMzZHJiN2cwdDA1M2pwOXlkbzVhb3kxIn0.lXwAZCDn_G9GNKcxWzYE7g"
                val resultado = repository.buscarLugar(_query.value, token = token)
                _resultadoGeocoding.value = resultado

                // Si hay resultados, sacamos las coordenadas del primer lugar
                resultado.features.firstOrNull()?.let { lugar ->
                    val lon = lugar.center[0]
                    val lat = lugar.center[1]

                    // URL del mapa estático
                    val url = "https://api.mapbox.com/styles/v1/mapbox/streets-v12/static/$lon,$lat,14,0/900x700?access_token=$token"
                    _urlMapa.value = url
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}