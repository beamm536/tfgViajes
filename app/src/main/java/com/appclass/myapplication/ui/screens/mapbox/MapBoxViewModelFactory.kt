package com.appclass.myapplication.ui.screens.mapbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appclass.myapplication.data_api.repository.MapBoxRepository



//esta clase no forma parte de la capa de datos :) por eso esta dnd las vistas
class MapBoxViewModelFactory (
    private val repository: MapBoxRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapBoxViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapBoxViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}