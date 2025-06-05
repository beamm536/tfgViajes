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


    //PANTALLAS PARA LA CREACION DE LAS RECOMENDACIONES
    object CrearRecomendacion1 : AppScreens("crearRecomendacion1")
    object CrearRecomendacion2 : AppScreens("crearRecomendacion2")
    object CrearRecomendacion3 : AppScreens("crearRecomendacion3")
    object CrearRecomendacion4 : AppScreens("crearRecomendacion4")

    //PANTALLA PARA EL LISTADO DE MIS RECOMENDACIONES
    object ListarRecomendaciones : AppScreens("listarRecomendaciones")

    //EDITAR RECOMENDACIONES
    object EditarRecomendaciones : AppScreens("editarRecomendacion/{recJson}"){
        fun createRoute(recJson: String): String = "editarRecomendacion/$recJson"
        const val RUTA_BASE = "editarRecomendacion"
        //fun createRoute(recJson: String) = "editarRecomendacion/$recJson"
    }

    //DETALLE RECOMENDACIONES - al pulsar en la lista de recomendaciones creadas
    object DetalleRecomendacionesPersonalizadas : AppScreens("detalleRecomendacion/{recJson}") {
        fun createRoute(recJson: String) = "detalleRecomendacion/$recJson"
    }
}