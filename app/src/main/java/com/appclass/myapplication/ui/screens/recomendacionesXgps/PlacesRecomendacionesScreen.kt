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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.appclass.myapplication.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.appclass.myapplication.data.dataStore.DataStoreManager
import com.appclass.myapplication.navigation.AppScreens
import com.appclass.myapplication.ui.components.barraNavegacion.BottomNavBar
import com.appclass.myapplication.ui.components.barraNavegacion.NavigationViewModel
import com.appclass.myapplication.ui.screens.permisos.getUserLocation
import com.appclass.myapplication.ui.theme.Poppins
import com.appclass.myapplication.ui.utils.obtenerSaludo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson

//---------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesRecomendacionesScreen(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel
    //viewModel: PlacesRecomendacionesViewModel = viewModel()
) {
    //val context = LocalContext.current
    val context = LocalContext.current
    val owner = LocalViewModelStoreOwner.current
    val dataStoreManager = remember { DataStoreManager(context) }

    val viewModel: PlacesRecomendacionesViewModel = viewModel(
        viewModelStoreOwner = owner ?: error ("no viewmodel store found"),
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PlacesRecomendacionesViewModel(dataStoreManager) as T
            }
        }
    )

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
//    Scaffold(
//        containerColor = Color(0xFFF0FAF6),
//        bottomBar = {
//            BottomNavBar(navController = navController, viewModel = navigationViewModel)
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//        ) {
////AQUI ESTABA EL BLOQUE DEL IF SIN EL WHEN
//            when {
//                loading -> {
//                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
//                }
//
//                places.isEmpty() -> {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(24.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.no_result),
//                                contentDescription = "file search",
//                                modifier = Modifier
//                                    .size(180.dp)
//                                    .padding(bottom = 16.dp)
//                            )
//                            Text(
//                                text = "No se encontraron lugares",
//                                fontSize = 20.sp,
//                                fontFamily = Poppins,
//                                fontWeight = FontWeight.Bold,
//                                modifier = Modifier.padding(16.dp)
//                            )
//                        }
//                    }
//                }
//
//                else -> {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(horizontal = 16.dp)
//                    ) {
//                        Text(
//                            text = " $saludo",
//                            style = MaterialTheme.typography.headlineSmall,
//                            modifier = Modifier.padding(top = 16.dp)
//                        )
//                        Text(
//                            text = "🌍 Explora el mundo a tu alrededor",
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                        Text(
//                            text = "Estas son nuestras recomendaciones cerca de ti",
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = MaterialTheme.colorScheme.onSurfaceVariant
//                        )
//
//                        Spacer(Modifier.height(16.dp))
//
//                        LazyVerticalGrid(
//                            columns = GridCells.Fixed(2),
//                            modifier = Modifier.fillMaxSize(),
//                            contentPadding = PaddingValues(bottom = 16.dp),
//                            verticalArrangement = Arrangement.spacedBy(12.dp),
//                            horizontalArrangement = Arrangement.spacedBy(12.dp)
//                        ) {
//                            items(places, key = { it.placeId ?: it.name }) { place ->
//                                PlaceCardGrid(
//                                    place = place,
//                                    navController = navController,
//                                    onFavoriteClick = {
//                                        place.placeId?.let {
//                                            viewModel.favoritos(it)
//                                        }
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//    }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(110.dp),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.primerlogo_app_removebg_preview),
                            contentDescription = "App logo",
                            modifier = Modifier
                                .size(56.dp)
                                .padding(end = 12.dp),
                            tint = Color.Unspecified
                        )

                        Column {
                            Text(
                                text = "Explora el mundo a tu alrededor",
                                style = MaterialTheme.typography.titleMedium,
                                fontFamily = Poppins
                            )
                            Text(
                                text = saludo,
                                style = MaterialTheme.typography.headlineSmall,
                                fontFamily = Poppins,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Unspecified
                )
            )
        },
        containerColor = Color(0xFFF0FAF6),
        bottomBar = {
            BottomNavBar(navController = navController, viewModel = navigationViewModel)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                loading -> {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                places.isNotEmpty() -> {

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        //Text(" $saludo", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(top = 12.dp))
                        //Text("🌍 Explora el mundo a tu alrededor", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Estas son nuestras recomendaciones cerca de ti",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(Modifier.height(16.dp))

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(places, key = { it.placeId ?: it.name }) { place ->
                                PlaceCardGrid(
                                    place = place,
                                    navController = navController,
                                    onFavoriteClick = {
                                        place.placeId?.let { viewModel.favoritos(it) }
                                    }
                                )
                            }
                        }
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.no_result),
                                contentDescription = "No lugares",
                                modifier = Modifier
                                    .size(180.dp)
                                    .padding(bottom = 16.dp)
                            )
                            Text(
                                text = "No se encontraron lugares",
                                fontSize = 20.sp,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun PlaceCardGrid(
    place: PlaceRecomendaciones,
    navController: NavHostController,
    onFavoriteClick: () -> Unit
) {

    val lightBlue = Color(0xFFAEE6F4)//0xFFADDDF0

    val bgColor = if (place.isFavourite)
        MaterialTheme.colorScheme.primaryContainer
    else
        MaterialTheme.colorScheme.surface

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f) // hace la tarjeta más cuadrada
            .clickable {
                navController.navigate("placeDetail/${place.placeId}")
            },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = lightBlue)
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
                            contentDescription = if (place.isFavourite) "Desmarcar favorito" else "marcar como favorito"
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


