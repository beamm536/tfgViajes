package com.appclass.myapplication.navigation

sealed class AppScreens(val ruta: String) {

    object Login: AppScreens("Login")
    object Registro: AppScreens("Registro")
}