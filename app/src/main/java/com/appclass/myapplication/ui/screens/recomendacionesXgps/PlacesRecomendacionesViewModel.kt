package com.appclass.myapplication.ui.screens.recomendacionesXgps

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appclass.myapplication.data.dataStore.DataStoreManager
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.PlaceRecomendaciones
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.RetrofitClient
import com.appclass.myapplication.models.Favorito
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PlacesRecomendacionesViewModel(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _places =
        MutableStateFlow<List<PlaceRecomendaciones>>(emptyList()) //sin la *s*  --> ref a la data class de PlaceRecomendaciones
    val places: StateFlow<List<PlaceRecomendaciones>> = _places

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _favoritos = MutableStateFlow<List<Favorito>>(emptyList())
    val favoritos: StateFlow<List<Favorito>> = _favoritos

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

    //    fun favoritos(placeId: String) {
//        val isFavNow = _places.value.firstOrNull { it.placeId == placeId }?.isFavourite ?: false
//
//        // ⚡ Cambiar visiblemente el favorito ya
//        _places.value = _places.value.map { place ->
//            if (place.placeId == placeId) {
//               // place.copy(isFavourite = !place.isFavourite)
//                place.copy(isFavourite = !isFavNow)
//            } else place
//        }
//
//        // 🧠 Persistir el cambio en DataStore (esto ya es asincrónico y lento)
//        viewModelScope.launch {
//            dataStoreManager.toggleFavorite(placeId)
//        }
//    }
    fun toggleFavoritoFirestore(place: PlaceRecomendaciones) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val firestore = FirebaseFirestore.getInstance()
        val favoritosRef = firestore.collection("favoritos")

        val lugarId = place.placeId ?: return

        val isFavNow = place.isFavourite
        _places.value = _places.value.map {
            if (it.placeId == lugarId) it.copy(isFavourite = !isFavNow) else it
        }

        if (!isFavNow) {
            val favorito = Favorito(
                userId = userId,
                lugarId = lugarId,
                nombreLugar = place.name,
                ubicacion = place.address ?: "",
                //descripcion = place.description ?: "",
                fechaGuardado = Timestamp.now()
            )
            favoritosRef.add(favorito)
                .addOnSuccessListener { docRef ->
                    val favoritoConId = favorito.copy(favoritoId = docRef.id)
                    _favoritos.value = _favoritos.value + favoritoConId

                    //forzamos la recarga por desincronizacion
                    cargarFavsDeFirestore()
                }
        } else {
            favoritosRef
                .whereEqualTo("userId", userId)
                .whereEqualTo("lugarId", lugarId)
                .get()
                .addOnSuccessListener { docs ->
                    for (doc in docs) {
                        favoritosRef.document(doc.id).delete()
                        // _favoritos.value = _favoritos.value.filterNot { it.favoritoId == doc.id }

                    }
                }
        }
    }


    fun cargarFavsDeFirestore() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseFirestore.getInstance()
            .collection("favoritos")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { docs ->
                val nuevos = docs.mapNotNull {
                    it.toObject(Favorito::class.java).copy(favoritoId = it.id)
                }
                _favoritos.value = nuevos
            }
    }

}