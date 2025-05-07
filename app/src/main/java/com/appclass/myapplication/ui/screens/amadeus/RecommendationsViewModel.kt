package com.appclass.myapplication.ui.screens.amadeus

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appclass.myapplication.data_api.api.AmadeusRetrofitInstance
import com.appclass.myapplication.data_api.model.amadeus.AmadeusPoi
import com.appclass.myapplication.data_api.repository.RecomendacionesAmadeusRepository
import kotlinx.coroutines.launch

class RecommendationsViewModel (): ViewModel(){
    //private val repository: RecomendacionesAmadeusRepository
    private val repository = RecomendacionesAmadeusRepository(
        authService = AmadeusRetrofitInstance.authService,
        placesService = AmadeusRetrofitInstance.placesServices
    )


    private val _places = mutableStateOf<List<AmadeusPoi>>(emptyList())
    val places: State<List<AmadeusPoi>> = _places

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage


    fun fetchPlaces(lat: Double, lon: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = repository.getRecommendations(lat, lon)
                _places.value = result
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage ?: "desconocido"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}