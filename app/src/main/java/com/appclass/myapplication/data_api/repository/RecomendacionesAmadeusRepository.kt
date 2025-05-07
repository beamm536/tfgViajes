package com.appclass.myapplication.data_api.repository

import com.appclass.myapplication.data_api.api.AmadeusAuthService
import com.appclass.myapplication.data_api.api.AmadeusPlacesServices
import com.appclass.myapplication.data_api.model.amadeus.AmadeusPoi

class RecomendacionesAmadeusRepository(
    private val authService: AmadeusAuthService,
    private val placesService: AmadeusPlacesServices
) {

    private var cachedToken: String? = null


    suspend fun getRecommendations(lat: Double, lon: Double): List<AmadeusPoi> {
        // Si el token es null, obtenemos uno nuevo
        if (cachedToken == null) {
            try {
                // Log para ver si obtenemos el token correctamente
                println("🔑 Obteniendo el token de acceso...")
                val tokenResponse = authService.getAccessToken(
                    clientId = "1GMH5iA97KcBE8ikLXjf7MWt9VgxfmGh",
                    clientSecret = "J6sMg8nCmeTQhwsQ"
                )
                println("🔑 Token recibido: ${tokenResponse.accessToken}")
                cachedToken = "${tokenResponse.tokenType} ${tokenResponse.accessToken}"
            } catch (e: Exception) {
                println("❌ Error al obtener el token: ${e.localizedMessage}")
                throw e // Lanza el error para que lo capturemos en el ViewModel
            }
        }

        // Ahora, realizamos la petición para obtener los POIs
        try {
            println("📍 Obteniendo los puntos de interés...")
            val response = placesService.getPointsOfInterest(
                authHeader = cachedToken!!,
                latitude = lat,
                longitude = lon
            )
            println("📍 Puntos de interés obtenidos: ${response.data.size} resultados")
            return response.data // Retorna la lista de POIs
        } catch (e: Exception) {
            println("❌ Error al obtener los POIs: ${e.localizedMessage}")
            throw e // Lanza el error para que lo capturemos en el ViewModel
        }
    }

}