package com.appclass.myapplication.ui.screens.recomendacionesXgps

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appclass.myapplication.data.dataStore.DataStoreManager
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.PlaceRecomendaciones
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlacesRecomendacionesViewModel(
    private val dataStoreManager: DataStoreManager
) : ViewModel(){

    private val _places = MutableStateFlow<List<PlaceRecomendaciones>>(emptyList()) //sin la *s*  --> ref a la data class de PlaceRecomendaciones
    val places: StateFlow<List<PlaceRecomendaciones>> = _places

    private val _loading = MutableStateFlow(false)
    val loading : StateFlow<Boolean> = _loading

    init {
        viewModelScope.launch {
            dataStoreManager.favoritePlaceIds.collect { favIds ->
                Log.d("FAVORITOS", "favoritos guardados: $favIds")
                _places.value = _places.value.map {
                    it.copy(isFavourite = favIds.contains(it.placeId))
                }
            }
        }
    }


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

    //para la seccion de favoritos - toggle de favoritos (switch) +/-
//    fun favoritos(placeId: String){
//        viewModelScope.launch {
//            Log.d("FAVORITOS", "🔄 Toggle favorito: $placeId")
//            dataStoreManager.toggleFavorite(placeId)
//            _places.value = _places.value.map { place ->
//                if (place.placeId == placeId) {
//                    place.copy(isFavourite = !place.isFavourite)
//                } else {
//                    place
//                }
//            }
//        }
////        _places.value = _places.value.map { place ->
////            if (place.placeId == placeId) {
////                place.copy(isFavourite = !place.isFavourite)
////            }else{
////                place
////            }
////        }
//    }
//}

    fun favoritos(placeId: String) {
        val isFavNow = _places.value.firstOrNull { it.placeId == placeId }?.isFavourite ?: false

        // ⚡ Cambiar visiblemente el favorito ya
        _places.value = _places.value.map { place ->
            if (place.placeId == placeId) {
               // place.copy(isFavourite = !place.isFavourite)
                place.copy(isFavourite = !isFavNow)
            } else place
        }

        // 🧠 Persistir el cambio en DataStore (esto ya es asincrónico y lento)
        viewModelScope.launch {
            dataStoreManager.toggleFavorite(placeId)
        }
    }
}