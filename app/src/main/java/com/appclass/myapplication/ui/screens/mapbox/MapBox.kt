package com.appclass.myapplication.ui.screens.mapbox


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
import com.appclass.myapplication.data_api.api.MapBoxApi
import com.appclass.myapplication.data_api.repository.MapBoxRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//@Composable
//fun MapBox(navController: NavController, viewModel: MapBoxViewModel){
//    var query by remember { mutableStateOf("") }
//    val geocodingResult = viewModel.geocodingResult.collectAsState()
//
//
//    Column {
//        // Campo de texto para la consulta
//        TextField(
//            value = query,
//            onValueChange = { query = it },
//            label = { Text("Introduce un lugar") }
//        )
//
//        // Botón para buscar
//        Button(onClick = { viewModel.fetchGeocoding(query) }) {
//            Text("Buscar")
//        }
//
//        // Mostrar resultados
//        geocodingResult.value?.features?.forEach { feature ->
//            Text(text = feature.toString()) //todo: en proceso de testeo
//        }
//    }
//}


@Composable
fun MapBoxScreen(navController: NavController) {
    // Crear Retrofit (solo una vez)
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://api.mapbox.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Crear API y Repository
    val api = remember { retrofit.create(MapBoxApi::class.java) }
    val repository = remember { MapBoxRepository(api) }

    // Crear ViewModel con Factory
    val viewModel: MapBoxViewModel = viewModel(
        factory = MapBoxViewModelFactory(repository)
    )

    // Llamar a la UI real
    MapBox(navController, viewModel)
}

@Composable
fun MapBox(navController: NavController, viewModel: MapBoxViewModel) {
    var query by remember { mutableStateOf("") }
    val geocodingResult = viewModel.geocodingResult.collectAsState()

    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Introduce un lugar") }
        )

        Button(onClick = { viewModel.fetchGeocoding(query) }) {
            Text("Buscar")
        }

        geocodingResult.value?.features?.forEach { feature ->
            Text(text = feature.name) // Mostramos el nombre del lugar
        }
    }
}
