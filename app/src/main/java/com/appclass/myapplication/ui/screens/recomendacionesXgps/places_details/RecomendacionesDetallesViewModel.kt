package com.appclass.myapplication.ui.screens.recomendacionesXgps.places_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.DetallesResponse
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.RetrofitClient
import kotlinx.coroutines.launch

class RecomendacionesDetallesViewModel : ViewModel() {
    var placeDetails by mutableStateOf<DetallesResponse?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    fun loadDetails(placeId: String) {
        viewModelScope.launch {
            loading = true
            println("🔍 [ViewModel] Cargando detalles para placeId: $placeId")
            try {
                val response = RetrofitClient.apiService.getPlaceDetails(placeId)
                println("✅ [ViewModel] Respuesta recibida: $response")
                placeDetails = response
            } catch (e: Exception) {
                println("❌ [ViewModel] Error al obtener detalles: ${e.message}")
                e.printStackTrace()
            } finally {
                loading = false
                println("📦 [ViewModel] Carga finalizada")
            }
        }
    }
}