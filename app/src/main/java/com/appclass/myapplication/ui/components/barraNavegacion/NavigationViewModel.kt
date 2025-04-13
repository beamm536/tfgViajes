package com.appclass.myapplication.ui.components.barraNavegacion

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * con esta clase, el objetivo es ir guardando el estado de navegación -- MANEJO DEL ESTADO DE NAVEGACIÓN
 *
 * -------------
 * guardamos el estado (seleccionado/noSeleccionado) del icono qeu tengamos seleccionado en nuestro navbar
 * - mutableStateOf  --> hacemos uqe cualquier cambio de estado se refleje automaticamente en nuestra ui
 * - iniciamos con un item ya seleccionado al arrancar la applicación
 *
 * - fun onItemSelected, actualiza el estado del item que ha sido seleccionado (el boton del navbar que hemos tocado)
 */
class NavigationViewModel : ViewModel() {
    private val _iconoSeleccionado = mutableStateOf<NavItem>(NavItem.Search) //CAMBIAR DE ICONO AL DE HOME PARA QUE EL NAVBAR EMPIECE EN HOME
    val iconoSeleccionado: State<NavItem> = _iconoSeleccionado

    fun onItemSelected(item: NavItem) {
        _iconoSeleccionado.value = item
    }
}