package com.appclass.myapplication.ui.screens.recomendacionesXgps

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.PlaceRecomendaciones
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlacesRecomendacionesViewModel : ViewModel(){

    private val _places = MutableStateFlow<List<PlaceRecomendaciones>>(emptyList()) //sin la *s*  --> ref a la data class de PlaceRecomendaciones
    val places: StateFlow<List<PlaceRecomendaciones>> = _places

    private val _loading = MutableStateFlow(false)
    val loading : StateFlow<Boolean> = _loading

    fun loadPlaces(lat: Double, lng: Double) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val response = RetrofitClient.apiService.getRecommendations(lat, lng)
                Log.d("API_RESPONSE", "Recomendaciones recibidas: ${response.recommendations.size}")
                _places.value = response.recommendations
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API_ERROR", "Error al obtener lugares: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }
}