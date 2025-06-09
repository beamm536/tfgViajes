package com.appclass.myapplication.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.appclass.myapplication.ui.screens.mapbox.explorarLugares.StaticMapImage

@Composable
fun PruebaExplorarLugaresScreen(navController: NavHostController) {
    val mapUrl = construirStaticMapUrl(
        latitud = 40.4168,
        longitud = -3.7038
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Vista de prueba de mapa estático (Madrid)")
        Spacer(modifier = Modifier.height(16.dp))
        StaticMapImage(url = mapUrl)
    }
}

// Generador del URL de mapa estático
fun construirStaticMapUrl(latitud: Double, longitud: Double): String {
    val token = "pk.eyJ1Ijoid2F4ZXI1OSIsImEiOiJjbDMzZHJiN2cwdDA1M2pwOXlkbzVhb3kxIn0.lXwAZCDn_G9GNKcxWzYE7g"
    return "https://api.mapbox.com/styles/v1/mapbox/streets-v12/static/$longitud,$latitud,14,0/600x300?access_token=$token"
}