package com.appclass.myapplication.ui.screens.registro

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appclass.myapplication.data.AuthRepository

class RegistroViewModel: ViewModel() {

//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val dbFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//
//    fun registrarUsuario(){
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    //todo: guardar usuario en la base de datos
//                } else {
//                    //todo: mostrar error
//                }
//            }
//    }

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _apellidos = MutableLiveData<String>()
    val apellidos: LiveData<String> = _apellidos

    private val _fechaNacimiento = MutableLiveData<String>()
    val fechaNacimiento: LiveData<String> = _fechaNacimiento

    private val _genero = MutableLiveData<String>()
    val genero: LiveData<String> = _genero

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _registroEnable = MutableLiveData<Boolean>()
    val registroEnable: LiveData<Boolean> = _registroEnable

    // Nuevo LiveData para saber si el registro fue exitoso
    private val _registroExitoso = MutableLiveData<Boolean>()
    val registroExitoso: LiveData<Boolean> = _registroExitoso


    private val authRepository = AuthRepository()


    fun onNombreChanged(nombre: String) { _nombre.value = nombre }
    fun onApellidosChanged(apellidos: String) { _apellidos.value = apellidos }
    fun onFechaNacimientoChanged(fechaNacimiento: String) { _fechaNacimiento.value = fechaNacimiento }
    fun onGeneroChanged(genero: String) { _genero.value = genero }

    fun onEmailChanged(email: String) {
        _email.value = email
        _registroEnable.value = isValidEmail(email) && isValidPassword(_password.value ?: "")
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        _registroEnable.value = isValidEmail(_email.value ?: "") && isValidPassword(password)
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(password: String): Boolean = password.length >= 6

    fun registrarUsuaario(
        nombre: String,
        apellidos: String,
        fechaNacimiento: String,
        genero: String,
        email: String,
        password: String,
        callback: (Boolean, String?) -> Unit
    ){

        Log.d("RegistroViewModel", "Llamando a registrarUsuario() desde ViewModel con email: $email")

        //llamamos a la funcion YA CREADA de nuestra clase AuthRepository
       // authRepository.registrarUsuario(nombre, apellidos, fechaNacimiento, genero,email, password, callback)
        authRepository.registrarUsuario(nombre, apellidos, fechaNacimiento, genero, email, password) { success, errorMsg ->
            _registroExitoso.postValue(success) // Actualizamos LiveData
            callback(success, errorMsg) // También seguimos usando el callback si lo necesitás en la vista
        }
    }

}