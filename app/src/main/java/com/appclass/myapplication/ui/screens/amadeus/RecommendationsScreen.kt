package com.appclass.myapplication.ui.screens.amadeus

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.appclass.myapplication.data_api.api.AmadeusAuthService
import com.appclass.myapplication.data_api.api.AmadeusPlacesServices
import com.appclass.myapplication.data_api.repository.RecomendacionesAmadeusRepository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") //para evitar warnings molestos - en este caso el de padding values q no se esta utilizando
@Composable
fun RecommendationsScreen(navController: NavHostController) {
    val viewModel: RecommendationsViewModel = viewModel()

    val isLoading by viewModel.isLoading
    val places by viewModel.places
    val errorMessage by viewModel.errorMessage

    // Ejecuta la llamada al cargar la pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchPlaces(lat = 40.4168, lon = -3.7038) // Madrid
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Recomendaciones Amadeus") })
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            if (isLoading) {
                CircularProgressIndicator()
            }

            errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            places.forEach { poi ->
                Text(
                    text = "${poi.name} (${poi.category})",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}