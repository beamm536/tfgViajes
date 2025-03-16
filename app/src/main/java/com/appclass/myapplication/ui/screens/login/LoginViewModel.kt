package com.appclass.myapplication.ui.screens.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appclass.myapplication.data.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel(){

    private val authRepository = AuthRepository()

    private val _email= MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password= MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable= MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

//    private val _navigateToUser = MutableLiveData<Boolean>()
//    val navigateToUser: LiveData<Boolean> = _navigateToUser


    fun onLoginChange(email: String, password: String){
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    fun onEmailChanged(email: String) {
        _email.value = email
        _loginEnable.value = isValidEmail(email) && isValidPassword(_password.value ?: "")
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        _loginEnable.value = isValidEmail(_email.value ?: "") && isValidPassword(password)
    }

    private fun validateForm() {
        _loginEnable.value = isValidEmail(_email.value.orEmpty()) && isValidPassword(_password.value.orEmpty())
    }


    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(password: String): Boolean = password.length >= 6


    fun onLoginSelected(afterLogin: () -> Unit) {
        val emailValue = _email.value ?: ""
        val passwordValue = _password.value ?: ""

        authRepository.iniciarSesion(emailValue, passwordValue) { success, _ ->
            if (success) {
                afterLogin()
            }
        }
    }


//    fun loginConGoogle(credential: AuthCredential, onLoginSuccess: () -> Unit) {
//        authRepository.loginConGoogle(credential) {
//            onLoginSuccess()
//        }
//    }




//    fun resetNavigation() {
//        _navigateToUser.value = false
//    }


//--------------------------------------------------------------


//    fun inciarSesion(email: String, password: String, callback: (Boolean, String?) -> Unit){
//        //llamamos a la funcion YA CREADA de nuestra clase AuthRepository
//        authRepository.iniciarSesion(email, password, callback)
//    }

}