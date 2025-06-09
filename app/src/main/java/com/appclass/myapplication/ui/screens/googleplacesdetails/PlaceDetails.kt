package com.appclass.myapplication.ui.screens.googleplacesdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appclass.myapplication.ui.utils.buildImageUrl
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

import coil.compose.rememberAsyncImagePainter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.navigation.NavBackStackEntry
import com.appclass.myapplication.data_api.model.googlePlaces.PlaceItem
import com.appclass.myapplication.data_api.model.googlePlaces.featuredPlaces


@Composable
fun PlaceDetails(
    navController: NavHostController,
    viewModel: PlaceDetailsViewModel,
    navBackStackEntry: NavBackStackEntry
) {

    val placeId = navBackStackEntry.arguments?.getString("placeId")
    val place by viewModel.placeResult.collectAsState()
    val apiKey = "AIzaSyBWNsg0IDYVrLyi3KokioyvmvCu6iH78AM"

    LaunchedEffect(placeId) {
        placeId?.let { viewModel.fetchPlaceDetails(it, apiKey) }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        place?.let {
            it.photos?.firstOrNull()?.photo_reference?.let { ref ->
                val imageUrl = buildImageUrl(ref, apiKey)
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Foto del lugar",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp)
                )
            }

            Text(text = "📍 Nombre: ${it.name}")
            Text(text = "📫 Dirección: ${it.formatted_address}")
            Text(text = "⭐ Rating: ${it.rating ?: "No disponible"}")

            it.editorial_summary?.overview?.let { desc ->
                Text(text = "\n📝 Descripción:\n$desc")
            }

            it.website?.let { site ->
                Text(text = "\n🌐 Sitio Web: $site", color = Color.Blue)
            }
        } ?: Text("Cargando detalles del lugar...")
    }
}



