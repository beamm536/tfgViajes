package com.appclass.myapplication.navigation

sealed class AppScreens(val ruta: String) {

    object MapBox: AppScreens("MapBox")

    object Auth: AppScreens("Auth")
    object Login: AppScreens("Login")
    object Registro: AppScreens("Registro")
    object Usuario: AppScreens("Usuario")
    object EditarPerfil: AppScreens("EditarPerfil")
}