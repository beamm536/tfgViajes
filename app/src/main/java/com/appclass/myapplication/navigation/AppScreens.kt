package com.appclass.myapplication.navigation

sealed class AppScreens(val ruta: String) {

    object Auth: AppScreens("Auth")
    object Login: AppScreens("Login")
    object Registro: AppScreens("Registro")
}