package com.appclass.myapplication.ui.screens.mapbox


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.text.style.TextAlign
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
                    viewModel.onQueryChanged(it) //actualizar solo el texto
                    //viewModel.fetchGeocoding(it)
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
//        LazyRow(
//            contentPadding = PaddingValues(horizontal = 16.dp)
//        ) {
//            items(viewModel.filters) { filter ->
//                FilterChip(
//                    selected = filter.isSelected,
//                    onClick = {
//                        viewModel.onFilterSelected(filter)
//                        viewModel.fetchGeocoding(viewModel.query) // busca con filtros actualizados
//                    },
//                    label = { Text(filter.name) },
//                    leadingIcon = { Icon(filter.icon, contentDescription = null) },
//                    modifier = Modifier.padding(end = 8.dp)
//                )
//            }
//        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(viewModel.filters) { filter ->
                SelectableCircleChip(
                    icon = filter.icon,
                    label = filter.name,
                    selected = filter.isSelected,
                    onSelectedChange = {
                        viewModel.onFilterSelected(filter)
                        viewModel.fetchGeocoding(viewModel.query)
                    }
                )
            }
        }

        //----------
        Spacer(modifier = Modifier.height(16.dp))

        //---------- para las busquedas recientes

        /**
         * - comprueba si hay busquedas recientes
         * - muestra un titulo
         * - cuando el usr ,pulsa una rellena el textfield y ejecuta fetchGeocoding()
         */
        if (viewModel.busquedasRecientes.isNotEmpty()) { //llamda a la funcion que tenemos en el viewmodel
            Text(
                text = "Búsquedas recientes:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(viewModel.busquedasRecientes) { recent ->
                    Button(
                        onClick = {
                            viewModel.onQueryChanged(recent)
                            viewModel.fetchGeocoding(recent)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(text = recent)
                    }
                }
            }
        }


        //----------
        Spacer(modifier = Modifier.height(16.dp))

        // mapa
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

//------------------------------


@Composable
fun SelectableCircleChip(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
    val textColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(4.dp)
            .clickable { onSelectedChange(!selected) }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .border(2.dp, borderColor, CircleShape)
                .padding(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = textColor
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
    }
}


