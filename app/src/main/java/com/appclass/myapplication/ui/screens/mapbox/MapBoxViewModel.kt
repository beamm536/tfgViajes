package com.appclass.myapplication.ui.screens.mapbox

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appclass.myapplication.data.dataStore.BusquedasRecientesUser
import com.appclass.myapplication.data_api.model.GeocodingResponse
import com.appclass.myapplication.data_api.repository.MapBoxRepository
import com.appclass.myapplication.data_api.repository.MapBoxStaticImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class MapBoxViewModel(
    private val repository: MapBoxRepository, //api geocoding - REPOSITORY
    private val staticImagesRepository: MapBoxStaticImagesRepository, //api staticimages - REPOSITORY
    private val busquedasRecientesUser: BusquedasRecientesUser
): ViewModel() {

    //barra de filtros-buscador
    var query by mutableStateOf("")
        private set

    fun onQueryChanged(newQuery: String) {
        query = newQuery
    }

    //LISTA DE FILTROS
    var filters by mutableStateOf(
        listOf(
            Filter("Madrid", Icons.Default.Person),
            Filter("Barcelona", Icons.Default.AccountCircle),
            Filter("Galicia", Icons.Default.Home),
            Filter("Cascada", Icons.Default.LocationOn)
        )
    )
        private set

    //val busquedasRecientes = mutableStateListOf<String>()
    val busquedasRecientesFlow: Flow<List<String>> = busquedasRecientesUser.busquedasRecientes

    fun onFilterSelected(filter: Filter) {
        filters = filters.map {
            if (it == filter) it.copy(isSelected = !it.isSelected) else it
        }
    }


//---------------------
    private val _geocodingResult = MutableStateFlow<GeocodingResponse?>(null)
    val geocodingResult: StateFlow<GeocodingResponse?> = _geocodingResult

    private val _staticMapUrl = MutableStateFlow<String?>(null)
    val staticMapUrl: StateFlow<String?> = _staticMapUrl
//---------------------


    fun fetchGeocoding(query: String) {
        viewModelScope.launch {
            try {
                // Obtenemos los filtros activos
                val activeFilters = filters.filter { it.isSelected }.joinToString(" ") { it.name }

                // Construimos la query final
                val finalQuery = if (activeFilters.isNotEmpty()) "$query $activeFilters" else query

                val response = repository.getGeocoding(finalQuery)
                guardarBusquedaReciente(query)//con esto me aseguro de que se guarden querys despues de hacer la busqueda
                _geocodingResult.value = response


                Log.d("MapBoxDebug", "Query enviada: $finalQuery")
                Log.d("MapBoxDebug", "Respuesta completa: $response")
                Log.d("MapBoxDebug", "Features: ${response.features}")

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


    /**FUNCION PARA ALMACENAR LAS BUSQUEDAS RECIENTES DEL USUARIO*/
//    fun guardarBusquedasRecientes(query: String){
//        if(query.isBlank()) return
//        busquedasRecientes.remove(query)
//        busquedasRecientes.add(0, query)
//        if (busquedasRecientes.size > 5){
//            busquedasRecientes.removeAt(busquedasRecientes.lastIndex)
//            //busquedasRecientes.removeLast()
//        }
//    }
    // Guarda una búsqueda reciente en DataStore
    fun guardarBusquedaReciente(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            busquedasRecientesUser.guardarBusqueda(query)
        }
    }


}