package com.appclass.myapplication.navigation

sealed class AppScreens(val ruta: String) {

    object MapBox: AppScreens("MapBoxScreen") //esta es mi pagina de busqueda q le cambie el nombre sin querer :v

    object Auth: AppScreens("Auth")
    object Login: AppScreens("Login")
    object Registro: AppScreens("Registro")
    object Usuario: AppScreens("Usuario")
    object EditarPerfil: AppScreens("EditarPerfil")
    object MapOnly: AppScreens("MapOnlyScreen")

    object PlaceDetailsGoogle: AppScreens("PlaceDetails")

    object DetalleMapa : AppScreens("detalleMapa/{nombre}/{lat}/{lon}") {
        fun createRoute(nombre: String, lat: Double, lon: Double): String {
            return "detalleMapa/$nombre/$lat/$lon"
        }
    }

    object PruebaExplorarLugares : AppScreens("PruebaExplorarLugares")

}