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
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


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
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(places) { place ->
                    PlaceCard(place)
                }
            }
        }
    }
}

@Composable
fun PlaceCard(placeR: PlaceRecomendaciones) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = placeR.name, style = MaterialTheme.typography.titleMedium)
            placeR.address?.let {
                Text(text = it, style = MaterialTheme.typography.bodyMedium)
            }
            placeR.photoUrl?.let { url ->
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
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