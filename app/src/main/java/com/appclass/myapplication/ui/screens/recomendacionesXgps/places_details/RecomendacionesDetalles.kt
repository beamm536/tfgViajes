package com.appclass.myapplication.ui.screens.recomendacionesXgps.places_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.PlaceRecomendaciones
import com.appclass.myapplication.ui.theme.Poppins


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

    Scaffold(
        topBar = {
        TopAppBar(
            title = { Text(text = "Detalles del lugar", fontFamily = Poppins) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFF0FAF6),
                titleContentColor = Color.Unspecified
            )
        )
    }
    ) { padding ->
        if (loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            place?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(padding)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    // Imagen principal
                    it.photoUrl?.let { url ->
                        Image(
                            painter = rememberAsyncImagePainter(url),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                               // .clip(RoundedCornerShape(24.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(
                        //modifier = Modifier.padding(16.dp)
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color(0xFFF0FAF6), //0xFFFFD1DC   0xFFD1FFF6
                                        Color(0xFF488091)//0xFFB5E0FF
                                    )
                                ),
                                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                            )
                            .padding(20.dp)
                    ) {
                        Text(it.name, style = MaterialTheme.typography.headlineSmall, fontFamily = Poppins)
                        Spacer(Modifier.height(4.dp))

                        it.address?.let { address ->
                            Text("📍 $address", style = MaterialTheme.typography.bodySmall)
                        }

                        Spacer(Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            repeat(5) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
//                            it.rating?.let { rating ->
//                                Text("$rating", style = MaterialTheme.typography.bodySmall)
//                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        Text("Descripción", style = MaterialTheme.typography.titleMedium, fontFamily = Poppins)
                        Text(
                            text = it.summary ?: "Sin descripción disponible.",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = Poppins
                        )

                        Spacer(Modifier.height(24.dp))

//                        it.openingHours?.let { hours ->
//                            Text("🕒 Horarios", style = MaterialTheme.typography.titleMedium, fontFamily = Poppins)
//                            Spacer(Modifier.height(4.dp))
//                            hours.forEach { hour ->
//                                Text(text = "- $hour", style = MaterialTheme.typography.bodySmall)
//                            }
//                            Spacer(Modifier.height(16.dp))
//                        }
                        it.openingHours?.let { hours ->
                            Text("🕒 Horarios", style = MaterialTheme.typography.titleMedium, fontFamily = Poppins)
                            Spacer(Modifier.height(4.dp))

                            Column {
                                hours.forEach { hour ->
                                    val parts = hour.split(":", limit = 2)
                                    val day = parts.getOrNull(0)?.trim() ?: ""
                                    val time = parts.getOrNull(1)?.trim() ?: ""

                                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                        Text(
                                            text = day,
                                            modifier = Modifier.width(90.dp),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Text(
                                            text = time,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }

                            Spacer(Modifier.height(16.dp))
                        }

                        it.reviews?.let { reviews ->
                            Text("🗣 Opiniones", style = MaterialTheme.typography.titleMedium, fontFamily = Poppins)
                            Spacer(Modifier.height(4.dp))
                            reviews.forEach { review ->
                                Text("⭐ ${review.rating} - ${review.authorName}", style = MaterialTheme.typography.bodySmall, fontFamily = Poppins)
                                Text(review.text, style = MaterialTheme.typography.bodySmall, fontFamily = Poppins)
                                Spacer(Modifier.height(8.dp))
                            }
                        }

                        it.suggestions?.let { suggestions ->
                            Spacer(Modifier.height(24.dp))
                            Text("✨ Sugerencias", style = MaterialTheme.typography.titleMedium, fontFamily = Poppins)
                            Spacer(Modifier.height(8.dp))
                            suggestions.forEach { suggestion ->
                                SuggestionItem(
                                    imageUrl = suggestion.imageUrl,
                                    text = suggestion.text
                                )
                            }
                        }
                    }
                }
            } ?: Text("⚠️ No se encontraron detalles.", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun SuggestionItem(imageUrl: String, text: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp),
                //.clip(RoundedCornerShape(12.dp)),
            //contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodySmall, fontFamily = Poppins)
    }
}