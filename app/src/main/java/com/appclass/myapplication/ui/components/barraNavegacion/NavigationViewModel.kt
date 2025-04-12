package com.appclass.myapplication.ui.components.barraNavegacion

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * con esta clase, el objetivo es ir guardando el estado de navegación
 */
class NavigationViewModel : ViewModel() {
    private val _iconoSeleccionado = mutableStateOf<NavItem>(NavItem.Search) //CAMBIAR DE ICONO AL DE HOME PARA QUE EL NAVBAR EMPIECE EN HOME
    val iconoSeleccionado: State<NavItem> = _iconoSeleccionado

    fun onItemSelected(item: NavItem) {
        _iconoSeleccionado.value = item
    }
}