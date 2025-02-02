package com.appclass.myapplication.ui.screens.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel(){

    private val _email= MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password= MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable= MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable


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

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(password: String): Boolean = password.length > 6

    fun onLoginSelected() {}

}