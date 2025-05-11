package com.appclass.myapplication.ui.screens.googleplacesdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appclass.myapplication.data_api.api.PlaceResult
import com.appclass.myapplication.data_api.api.RetrofitInstanceGoogle // la instancia de retrfit es la de google ¡cuidado que no se importe la primera!
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceDetailsViewModel : ViewModel() {

    // Flujo de estado para observar los resultados en la UI
    private val _placeResult = MutableStateFlow<PlaceResult?>(null)
    val placeResult: StateFlow<PlaceResult?> = _placeResult
//    var placeResult by mutableStateOf<PlaceResult?>(null)
//        private set

    fun fetchPlaceDetails(placeId: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstanceGoogle.api.getPlaceDetails(placeId, apiKey = apiKey)
                _placeResult.value = response.result
            } catch (e: Exception) {
                e.printStackTrace()
                _placeResult.value = null
            }
        }
    }

    //prueba inicial - comprobacion del correcto funcionamientro de la api
    init {
        fetchPlaceDetails(
            placeId = "ChIJPTacEpBQwokRKwIlDXelxkA",
            apiKey = "AIzaSyBWNsg0IDYVrLyi3KokioyvmvCu6iH78AM"
        )
    }

}