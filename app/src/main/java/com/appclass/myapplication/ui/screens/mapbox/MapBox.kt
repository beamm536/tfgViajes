package com.appclass.myapplication.ui.screens.mapbox


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import coil.compose.AsyncImage
//import coil3.compose.AsyncImage
import com.appclass.myapplication.data_api.api.MapBoxApiGeocoding
import com.appclass.myapplication.data_api.api.MapBoxApiStaticImage
import com.appclass.myapplication.data_api.repository.MapBoxRepository
import com.appclass.myapplication.data_api.repository.MapBoxStaticImagesRepository
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
    val api = remember { retrofit.create(MapBoxApiGeocoding::class.java) }
    val staticImagesApi = remember { retrofit.create(MapBoxApiStaticImage::class.java) }

    val repository = remember { MapBoxRepository(api) }
    val staticImagesRepo = remember { MapBoxStaticImagesRepository(staticImagesApi) }

    // Crear ViewModel con Factory
    val viewModel: MapBoxViewModel = viewModel(
        factory = MapBoxViewModelFactory(repository,  staticImagesRepo)
    )

    // Llamar a la UI real
    MapBox(navController, viewModel)
}

@Composable
fun MapBox(navController: NavController, viewModel: MapBoxViewModel) {
    var query by remember { mutableStateOf("") }
    val geocodingResult = viewModel.geocodingResult.collectAsState()
    val staticMapUrl by viewModel.staticMapUrl.collectAsState()

    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Introduce un lugar") }
        )

        Button(onClick = { viewModel.fetchGeocoding(query) }) {
            Text("Buscar")
        }

        LazyColumn {
            items(geocodingResult.value?.features ?: emptyList()) { feature ->
                Text(text = feature.placeName)
            }
        }

        staticMapUrl?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = "Mapa Estático"
            )
        }
//        geocodingResult.value?.features?.forEach { feature ->
//            Text(text = feature.placeName) //mostramos el nombre del lugar
//        }
        /**
         * el problema de que esto estuviera mal, era la importación de Feature en Geocoding
         */

    }
}

