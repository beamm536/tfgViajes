package com.appclass.myapplication.data.repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf


/**
 * esta clase es para que se pueda compartir la variable del switch, y se mantenga
 * el estado en el que esta se encuentra.
 * Que la vista cambie en funcion del valor de la variable que detecte
 */
class AuthViewModel {
    private val _isLoginSelected = mutableStateOf(true)
    val isLoginSelected: State<Boolean> get() = _isLoginSelected

    fun toggleSelection(isLogin: Boolean) {
        _isLoginSelected.value = isLogin
    }
}