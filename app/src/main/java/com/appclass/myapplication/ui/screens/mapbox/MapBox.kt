@file:OptIn(ExperimentalMaterial3Api::class)

package com.appclass.myapplication.ui.screens.mapbox

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.appclass.myapplication.data.dataStore.BusquedasRecientesUser
import com.appclass.myapplication.data_api.api.MapBoxApiGeocoding
import com.appclass.myapplication.data_api.api.MapBoxApiStaticImage
import com.appclass.myapplication.data_api.repository.MapBoxRepository
import com.appclass.myapplication.data_api.repository.MapBoxStaticImagesRepository
import com.appclass.myapplication.navigation.AppScreens
import com.appclass.myapplication.navigation.AppScreens.MapBox
import com.appclass.myapplication.ui.components.barraNavegacion.BottomNavBar
import com.appclass.myapplication.ui.components.barraNavegacion.NavigationViewModel
import com.appclass.myapplication.ui.theme.Poppins
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

    val context = LocalContext.current
    val busquedasRecientesUser = remember { BusquedasRecientesUser(context) }

    // Crear ViewModel con Factory
    val viewModel: MapBoxViewModel = viewModel(
        factory = MapBoxViewModelFactory(repository, staticImagesRepo, busquedasRecientesUser)
    )

    // Llamar a la UI real
    MapBox(navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBox(navController: NavController, viewModel: MapBoxViewModel) {
    var query by remember { mutableStateOf("") }
    val geocodingResult = viewModel.geocodingResult.collectAsState()
    val staticMapUrl by viewModel.staticMapUrl.collectAsState()

    // Recolecta las búsquedas recientes del ViewModel usando DataStore
    val busquedasRecientes by viewModel.busquedasRecientesFlow.collectAsState(initial = emptyList())


    val navigationViewModel: NavigationViewModel = viewModel()


    Scaffold(
        containerColor = Color(0xFF3B3B3B),
        bottomBar = {
            BottomNavBar(navController = navController, viewModel = navigationViewModel)
        }
    ) { innerPadding ->

        //COLUMNA PRINCIPAL DE LA VISTA
        Column(
            //verticalArrangement = Arrangement.Center,
            //horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Titulo
                    Text(
                        text = "Búsqueda",
                        fontFamily = Poppins,
                        style = MaterialTheme.typography.headlineLarge, //headlineMedium
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // Search + Filtros Icon
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        //.clip(RoundedCornerShape(16.dp))
                        //.background(Color.White)
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedCampoBusqueda(
                        query = query,
                        onQueryChange = {
                            query = it
                            viewModel.onQueryChanged(it)
                        },
                        onSearchClick = {
                            viewModel.fetchGeocoding(query)
                        },
                        onFilterClick = {
                            // Puedes abrir un modal de filtros o simplemente cambiar UI
                            println("Clic en filtros")
                        }
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "Categoría", //filtros
                    fontFamily = Poppins,
                    style = MaterialTheme.typography.headlineSmall, //headlineMedium
                    modifier = Modifier.padding(16.dp)
                )
                // Filtros horizontales
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
            }


            Spacer(modifier = Modifier.height(16.dp))

            //---------- para las busquedas recientes
            if (busquedasRecientes.isNotEmpty()) { // llamada a la función que tenemos en el viewModel
                Column {
                    Text(
                        text = "Búsquedas recientes:",
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = Poppins,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        items(busquedasRecientes) { recent ->
                            Button(
                                onClick = {
                                    query = recent
                                    viewModel.onQueryChanged(recent)
                                    viewModel.fetchGeocoding(recent)
                                },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(
                                    text = recent,
                                    fontFamily = Poppins
                                )
                            }
                        }
                    }
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
                items(geocodingResult.value?.features ?: emptyList()) { feature ->
                    PlaceCard(feature.placeName) {
                        val lat = feature.center[1]
                        val lon = feature.center[0]
                        val nombre = feature.placeName.replace("/", "_") // Evita fallos en la URL
                        navController.navigate(
                            AppScreens.DetalleMapa.createRoute(nombre, lat, lon)
                        )
                    }
                }
            }
        }//cierre del column principal
    }
}
@Composable
fun PlaceCard(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Ubicación...", style = MaterialTheme.typography.bodySmall)
        }
    }
}

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
            fontFamily = Poppins,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
    }
}



@Composable
fun OutlinedCampoBusqueda(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                "Buscar",
                color = Color.Gray,
                fontFamily = Poppins
            )
        },
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Lupa de busqueda",
                    tint = Color.Gray
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            focusedBorderColor = Color.Gray,
            cursorColor = Color.Gray,

        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.White)
    )
}
