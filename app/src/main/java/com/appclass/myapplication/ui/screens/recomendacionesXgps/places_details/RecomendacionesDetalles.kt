package com.appclass.myapplication.ui.screens.recomendacionesXgps.places_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.PlaceRecomendaciones

//@Composable
//fun RecomendacionesDetalles(place: PlaceRecomendaciones){ //param: places - class: PlaceRecomendaciones - package:api_repcomendacionXcoordenadas
//
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .padding(16.dp)) {
//
//        Text(text = place.name, style = MaterialTheme.typography.headlineSmall)
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        place.address?.let {
//            Text(text = "Dirección: $it", style = MaterialTheme.typography.bodyMedium)
//        }
//
//        place.rating?.let {
//            Text(text = "Valoración: $it", style = MaterialTheme.typography.bodyMedium)
//        }
//
//        place.reviews?.let {
//            Text(text = "Reseñas: $it", style = MaterialTheme.typography.bodyMedium)
//        }
//
//        place.photoUrl?.let { url ->
//            Spacer(modifier = Modifier.height(8.dp))
//            Image(
//                painter = rememberAsyncImagePainter(url),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp),
//                contentScale = ContentScale.Crop
//            )
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecomendacionesDetalles(
    placeId: String,
    navController: NavHostController,
    viewModel: RecomendacionesDetallesViewModel = viewModel()
) {
    val place = viewModel.placeDetails
    val loading = viewModel.loading

    LaunchedEffect(placeId) {
        println("🧭 [UI] Lanzando carga para: $placeId")
        viewModel.loadDetails(placeId)
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Detalles del lugar") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
            }
        )
    }) { padding ->
        if (loading) {
            println("⏳ [UI] Mostrando loading")
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            place?.let {
                println("🟢 [UI] Mostrando detalles para: ${it.name}")
                Column(
                    Modifier
                        .padding(paddingValues = padding)
                        .then(Modifier.padding(16.dp))
                        .verticalScroll(rememberScrollState())
                ) {
                    it.photoUrl?.let { url ->
                        Image(
                            painter = rememberAsyncImagePainter(url),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.height(16.dp))
                    }

                    Text(text = it.name, style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    it.address?.let { Text(it) }
                    it.phone?.let { Text("Tel: $it") }
                    it.website?.let { Text("Web: $it") }

                    Spacer(Modifier.height(16.dp))

                    it.openingHours?.let {
                        Text("Horario:")
                        it.forEach { hour -> Text("- $hour") }
                    }

                    Spacer(Modifier.height(16.dp))

                    it.reviews?.let { reviews ->
                        Text("Reseñas:", style = MaterialTheme.typography.titleMedium)
                        reviews.forEach { review ->
                            Text("⭐ ${review.rating} - ${review.authorName}")
                            Text(review.text, style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            } ?: run {
                println("⚠️ [UI] place es null, no se encontraron detalles.")
                Text("No se encontraron detalles.")
            }
        }
    }
}