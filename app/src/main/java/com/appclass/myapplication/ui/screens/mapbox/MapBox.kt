package com.appclass.myapplication.ui.screens.mapbox


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.unit.dp
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


    Column(modifier = Modifier.fillMaxSize()) {

        // Titulo
        Text(
            text = "Búsqueda",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        // Search + Filtros Icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = viewModel.query,
                onValueChange = {
                    viewModel.onQueryChanged(it)
                    viewModel.fetchGeocoding(it)
                },
                placeholder = { Text("Buscar") },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { viewModel.fetchGeocoding((viewModel.query)) }) {
                Icon(Icons.Default.Search, contentDescription = "Búsqueda manual")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Filtros horizontales
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(viewModel.filters) { filter ->
                FilterChip(
                    selected = filter.isSelected,
                    onClick = {
                        viewModel.onFilterSelected(filter)
                        viewModel.fetchGeocoding(viewModel.query) // busca con filtros actualizados
                    },
                    label = { Text(filter.name) },
                    leadingIcon = { Icon(filter.icon, contentDescription = null) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mapa
        staticMapUrl?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = "Mapa",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Resultados
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(geocodingResult.value?.features ?: emptyList()){ feature ->
                PlaceCard(feature.placeName)
            }
        }
    }
}

@Composable
fun PlaceCard(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Ubicación...", style = MaterialTheme.typography.bodySmall)
        }
    }
}

//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally // Opcional, centra contenido horizontalmente dentro de la Column
//        ) {
//        TextField(
//            value = query,
//            onValueChange = { query = it },
//            label = { Text("Introduce un lugar") }
//        )
//
//        Button(onClick = { viewModel.fetchGeocoding(query) }) {
//            Text("Buscar")
//        }
//
//        LazyColumn {
//            items(geocodingResult.value?.features ?: emptyList()) { feature ->
//                Text(text = feature.placeName)
//            }
//        }
//
//        staticMapUrl?.let { url ->
//            AsyncImage(
//                model = url,
//                contentDescription = "Mapa Estático"
//            )
//        }
////        geocodingResult.value?.features?.forEach { feature ->
////            Text(text = feature.placeName) //mostramos el nombre del lugar
////        }
//        /**
//         * el problema de que esto estuviera mal, era la importación de Feature en Geocoding
//         */
//
//        }
//    }


