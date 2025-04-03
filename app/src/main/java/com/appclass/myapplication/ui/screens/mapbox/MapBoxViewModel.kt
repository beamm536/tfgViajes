package com.appclass.myapplication.ui.screens.mapbox

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appclass.myapplication.data_api.model.GeocodingResponse
import com.appclass.myapplication.data_api.repository.MapBoxRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapBoxViewModel(private val repository: MapBoxRepository): ViewModel() {

    private val _geocodingResult = MutableStateFlow<GeocodingResponse?>(null)
    val geocodingResult: StateFlow<GeocodingResponse?> = _geocodingResult

//    fun fetchGeocoding(query: String) {
//        viewModelScope.launch {
//            try {
//                val response = repository.getGeocoding(query)
//                _geocodingResult.value = response
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
//    }
fun fetchGeocoding(query: String) {
    viewModelScope.launch {
        try {
            val response = repository.getGeocoding(query)
            Log.d("MapBoxDebug", "Respuesta completa: $response")
            Log.d("MapBoxDebug", "Features: ${response.features}")
            _geocodingResult.value = response
        } catch (e: Exception) {
            Log.e("MapBoxError", "Error en fetchGeocoding", e)
        }
    }
}



}