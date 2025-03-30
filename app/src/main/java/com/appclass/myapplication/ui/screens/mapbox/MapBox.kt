package com.appclass.myapplication.ui.screens.mapbox

import com.appclass.myapplication.data_api.model.Feature

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun MapBox(navController: NavController, viewModel: MapBoxViewModel){
    var query by remember { mutableStateOf("") }
    val geocodingResult = viewModel.geocodingResult.collectAsState()


    Column {
        // Campo de texto para la consulta
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Introduce un lugar") }
        )

        // Botón para buscar
        Button(onClick = { viewModel.fetchGeocoding(query) }) {
            Text("Buscar")
        }

        // Mostrar resultados
        geocodingResult.value?.features?.forEach { feature ->
            Text(text = feature.)
        }
    }
}
