package com.appclass.myapplication.ui.screens.cambioVistasSwitch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * esta clase es para que se pueda compartir la variable del switch, y se mantenga
 * el estado en el que esta se encuentra.
 * Que la vista cambie en funcion del valor de la variable que detecte
 */
class AuthViewModel: ViewModel() {

    private val _isLoginSelected = MutableLiveData(true)
    val isLoginSelected: LiveData<Boolean> = _isLoginSelected
    //almacenaremos true si estamos en el login, false si estamos en el registro gracias al LiveData

    fun toggleSelection(isLogin: Boolean) { //para que podamos cambiar entre vistas
        _isLoginSelected.value = isLogin
    }
}