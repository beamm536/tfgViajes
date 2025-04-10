package com.appclass.myapplication.ui.screens.mapbox

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    //barra de filtros-buscador
    var query by mutableStateOf("")
        private set

    fun onQueryChanged(newQuery: String) {
        query = newQuery
    }

    var filters by mutableStateOf(
        listOf(
            Filter("Madrid", Icons.Default.Person),
            Filter("Barcelona", Icons.Default.AccountCircle),
            Filter("Galicia", Icons.Default.Home),
            Filter("Cascada", Icons.Default.LocationOn)
        )
    )
        private set

    fun onFilterSelected(filter: Filter) {
        filters = filters.map {
            if (it == filter) it.copy(isSelected = !it.isSelected) else it
        }
    }



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

            // Si hay resultados, cogemos las coordenadas del primer resultado
            response.features.firstOrNull()?.let { feature ->
                val lon = feature.center[0]
                val lat = feature.center[1]

                fetchStaticMap(lon, lat)
            }

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
                "https://api.mapbox.com/styles/v1/mapbox/streets-v12/static/$lon,$lat,14,0/900x700?access_token=${staticImagesRepository.accessToken}"
            _staticMapUrl.value = url
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



}