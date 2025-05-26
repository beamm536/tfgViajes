package com.appclass.myapplication.ui.screens.recomendacionesXgps

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.PlaceRecomendaciones




//--------
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.appclass.myapplication.navigation.AppScreens
import com.appclass.myapplication.ui.screens.permisos.getUserLocation
import com.appclass.myapplication.ui.utils.obtenerSaludo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson


//@Composable
//fun PlacesRecomendacionesScreen (navController: NavHostController, viewModel: PlacesRecomendacionesViewModel = viewModel ()){
//    val places by viewModel.places.collectAsState()
//    val loading by viewModel.loading.collectAsState()
//
//    // Carga inicial de lugares (puedes moverlo luego a eventos)
//    LaunchedEffect(Unit) {
//        viewModel.loadPlaces(40.4168, -3.7038) // Coordenadas de prueba
//    }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        if (loading) {
//            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
//        }
//
//        LazyColumn(modifier = Modifier.padding(16.dp)) {
//            items(places) { place ->
//                PlaceCard(place)
//            }
//        }
//    }
//}
//
//
//@Composable
//fun PlaceCard(placeR: PlaceRecomendaciones) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = placeR.name, style = MaterialTheme.typography.titleMedium)
//            placeR.address?.let {
//                Text(text = it, style = MaterialTheme.typography.bodyMedium)
//            }
//            placeR.photoUrl?.let { url ->
//                Spacer(modifier = Modifier.height(8.dp))
//                Image(
//                    painter = rememberAsyncImagePainter(url),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp),
//                    contentScale = ContentScale.Crop
//                )
//            }
//        }
//    }
//}


//---------------
@Composable
fun PlacesRecomendacionesScreen(
    navController: NavHostController,
    viewModel: PlacesRecomendacionesViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context as Activity
    val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getUserLocation(context) { lat, lng ->
                viewModel.loadPlaces(lat, lng)
            }
        }
    }

    val permissionGranted = remember {
        ContextCompat.checkSelfPermission(
            context,
            locationPermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    var requestedLocation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!requestedLocation) {
            if (permissionGranted) {
                getUserLocation(context) { lat, lng ->
                    viewModel.loadPlaces(lat, lng)  //LOCALIZACION REAL DEL DISPOSITIVO
                    //viewModel.loadPlaces(40.535112, -3.616192)  // Madrid 40.552624, -3.457556
                }
            } else {
                permissionLauncher.launch(locationPermission)
            }
            requestedLocation = true
        }
    }

    val places by viewModel.places.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val saludo = remember { obtenerSaludo() }

    LaunchedEffect(places) {
        Log.d("UI_STATE", "Lugares en UI: ${places.size}")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (places.isEmpty() && !loading) {
            Text(
                text = "No se encontraron lugares.",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = " $saludo",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "🌍 Explora el mundo a tu alrededor",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Estas son nuestras recomendaciones cerca de ti",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(places) { place ->
                        PlaceCardGrid(
                            place = place,
                            navController = navController,
                            onFavoriteClick = {
                                viewModel.favoritos(place.placeId ?: "")
                            }
                        )
                    }
                }
            }
        }

    }
}

//@Composable
//fun PlaceCard(placeR: PlaceRecomendaciones, onClick: () -> Unit) { //vamos a hacer que cada tarjeta eu saquemos de la api (las recomendaciones), que sean clikables
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .clickable { onClick() },
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = placeR.name, style = MaterialTheme.typography.titleMedium)
//            placeR.address?.let {
//                Text(text = it, style = MaterialTheme.typography.bodyMedium)
//            }
//            placeR.photoUrl?.let { url ->
//                Spacer(modifier = Modifier.height(8.dp))
//                Image(
//                    painter = rememberAsyncImagePainter(url),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp),
//                    contentScale = ContentScale.Crop
//                )
//            }
//        }
//    }
//}

//@Composable
//fun PlaceCard(placeR: PlaceRecomendaciones, navController: NavHostController) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .clickable {
//                navController.navigate("placeDetail/${placeR.placeId}")
//                //navController.navigate(AppScreens.RecomendacionesDetalles.createRoute(placeR.placeId.toString()))
//
//            },
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = placeR.name, style = MaterialTheme.typography.titleMedium)
//            placeR.address?.let {
//                Text(text = it, style = MaterialTheme.typography.bodyMedium)
//            }
//            placeR.photoUrl?.let { url ->
//                Spacer(modifier = Modifier.height(8.dp))
//                Image(
//                    painter = rememberAsyncImagePainter(url),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp),
//                    contentScale = ContentScale.Crop
//                )
//            }
////            placeR.placeDetail?.let { details ->
////                Spacer(modifier = Modifier.height(8.dp))
////                Text(text = "Detalles:", style = MaterialTheme.typography.labelMedium)
////                details.description?.let {
////                    Text(text = it, style = MaterialTheme.typography.bodySmall)
////                }
////                details.hours?.let {
////                    Text(text = "Horario: $it", style = MaterialTheme.typography.bodySmall)
////                }
////                details.website?.let {
////                    Text(text = "Sitio web: $it", style = MaterialTheme.typography.bodySmall)
////                }
////                details.phone?.let {
////                    Text(text = "Teléfono: $it", style = MaterialTheme.typography.bodySmall)
////                }
////            }
//        }
//    }
//}

//@Composable
//fun PlaceCard(place: PlaceRecomendaciones, navController: NavHostController) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 12.dp)
//            .clickable {
//                navController.navigate("placeDetail/${place.placeId}")
//            },
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//        shape = RoundedCornerShape(20.dp)
//    ) {
//        Column(modifier = Modifier.fillMaxWidth()) {
//
//            // Imagen destacada
//            place.photoUrl?.let { url ->
//                Image(
//                    painter = rememberAsyncImagePainter(url),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
//                    contentScale = ContentScale.Crop
//                )
//            }
//
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(
//                    text = place.name,
//                    style = MaterialTheme.typography.titleLarge,
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//
//                place.address?.let {
//                    Text(
//                        text = it,
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                }
//            }
//        }
//    }
//}


@Composable
fun PlaceCardGrid(
    place: PlaceRecomendaciones,
    navController: NavHostController,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f) // hace la tarjeta más cuadrada
            .clickable {
                navController.navigate("placeDetail/${place.placeId}")
            },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            place.photoUrl?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            imageVector = if (place.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Toggle Favorite"
                        )
                    }
                }
                place.address?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }
            }
        }
    }
}


// Función para obtener ubicación real
fun getUserLocation(context: android.content.Context, onLocationReady: (Double, Double) -> Unit) {
    val fusedClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    if (
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
    ) return

    fusedClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            onLocationReady(it.latitude, it.longitude)
        }
    }
}


