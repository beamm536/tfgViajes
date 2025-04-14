package com.appclass.myapplication.ui.screens.mapbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appclass.myapplication.data.dataStore.BusquedasRecientesUser
import com.appclass.myapplication.data_api.repository.MapBoxRepository
import com.appclass.myapplication.data_api.repository.MapBoxStaticImagesRepository

/**
 * Crear instancias de nuestro ViewModel
 *
 *  1. guarda las dependencias para crear el viewmodel
 *  2. viewmodelProvider.Factory - le dice a Android como crear el viewmodel con las dependecias que ha guardado
 *
 *  Esta clase es necesaria para el viewmodel, ya que de por sí esa clase no sabe que es lo que necesita para funcionar, un repo, una api...
 *  Los parámetros que le proporcionamos son:
 *  - MapBoxRepository: clase que se conecta a la API de MapBox
 *  - MapBoxStaticImagesRepository: para los mapas que uso en la ui de busqueda
 *  - BusquedasRecientesUser: guardado de las busquedas recientes
 */
//esta clase no forma parte de la capa de datos :) por eso esta dnd las vistas
class MapBoxViewModelFactory (
    private val repository: MapBoxRepository,
    private val staticImagesRepository: MapBoxStaticImagesRepository,
    private val busquedasRecientesUser: BusquedasRecientesUser
) : ViewModelProvider.Factory {
    /**
     * Se comprueba que se pueda crear la clase del viewmodel
     * Si se crea, lo hará con los parámetros que le hemos proporcionado
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapBoxViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") //con esto evitamos que nos de un warning o fallo en la terminal al hacer el casting en la siguiente linea "as T"
            return MapBoxViewModel(repository, staticImagesRepository, busquedasRecientesUser) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}