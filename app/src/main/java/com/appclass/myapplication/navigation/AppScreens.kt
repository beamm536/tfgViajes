package com.appclass.myapplication.navigation

sealed class AppScreens(val ruta: String) {

    object MapBox: AppScreens("MapBoxScreen")

    object Auth: AppScreens("Auth")
    object Login: AppScreens("Login")
    object Registro: AppScreens("Registro")
    object Usuario: AppScreens("Usuario")
    object EditarPerfil: AppScreens("EditarPerfil")
    object MapOnly: AppScreens("MapOnlyScreen")

    object DetalleMapa : AppScreens("detalleMapa/{nombre}/{lat}/{lon}") {
        fun createRoute(nombre: String, lat: Double, lon: Double): String {
            return "detalleMapa/$nombre/$lat/$lon"
        }
    }

}