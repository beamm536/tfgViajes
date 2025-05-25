package com.appclass.myapplication.navigation

sealed class AppScreens(val ruta: String) {

    object MapBox: AppScreens("MapBoxScreen") //esta es mi pagina de busqueda q le cambie el nombre sin querer :v

    object Auth: AppScreens("Auth")
    object Login: AppScreens("Login")
    object Registro: AppScreens("Registro")
    object Usuario: AppScreens("Usuario")
    object EditarPerfil: AppScreens("EditarPerfil")
    object MapOnly: AppScreens("MapOnlyScreen")

    object PlaceDetailsGoogle: AppScreens("placeDetail/{placeId}") //aqui antes tenia una s en placeDetails
    object Places: AppScreens("Places")

    object DetalleMapa : AppScreens("detalleMapa/{nombre}/{lat}/{lon}") {
        fun createRoute(nombre: String, lat: Double, lon: Double): String {
            return "detalleMapa/$nombre/$lat/$lon"
        }
    }

    object PruebaExplorarLugares : AppScreens("PruebaExplorarLugares")
    object PlacesRecomendacionesScreen : AppScreens("PlacesRecomendacionesScreen")

    object RecomendacionesDetalles : AppScreens("placeDetail/{placeId}"){
        fun createRoute(placeId: String): String = "placeDetail/$placeId"
    }

}