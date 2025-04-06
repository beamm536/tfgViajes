package com.appclass.myapplication.ui.screens.mapbox

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appclass.myapplication.data_api.model.GeocodingResponse
import com.appclass.myapplication.data_api.repository.MapBoxRepository
import com.appclass.myapplication.data_api.repository.MapBoxStaticImagesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapBoxViewModel(
    private val repository: MapBoxRepository,
    private val staticImagesRepository: MapBoxStaticImagesRepository
): ViewModel() {

    private val _geocodingResult = MutableStateFlow<GeocodingResponse?>(null)
    val geocodingResult: StateFlow<GeocodingResponse?> = _geocodingResult

    private val _staticMapUrl = MutableStateFlow<String?>(null)
    val staticMapUrl: StateFlow<String?> = _staticMapUrl

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

private suspend fun fetchStaticMap(lon: Double, lat:Double){
    try {
        val response = staticImagesRepository.getStaticMapImage(lon, lat)
        if (response.isSuccessful) {
            // Generas la url manual porque responseBody es imagen
            val url =
                "https://api.mapbox.com/styles/v1/mapbox/streets-v12/static/$lon,$lat,14,0/500x300?access_token=${staticImagesRepository.accessToken}"
            _staticMapUrl.value = url
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



}