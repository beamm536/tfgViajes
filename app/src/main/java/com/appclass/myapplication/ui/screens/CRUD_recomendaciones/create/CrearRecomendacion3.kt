package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.FilterChip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.appclass.myapplication.R

//vista para la localizacion de la recomendacion - vista3
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun CrearRecomendacion3 (
//    viewModel: RecomendacionViewModel = viewModel(),
//    onNext: () -> Unit,
//    onBack: () -> Unit
//) {
//    val locationName by viewModel.locationName
//    val selectedCategory by viewModel.category
//    val context = LocalContext.current
//
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//
//    val categorias = listOf("Comida", "Naturaleza", "Arte", "Cultura", "Vida Nocturna", "Otro")
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.Top
//    ) {
//        Text(
//            text = "¿Dónde está este lugar?",
//            fontSize = 22.sp,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        TextField(
//            value = locationName,
//            onValueChange = { viewModel.onLocationChanged(it) },
//            placeholder = { Text("Ej: Barcelona, España") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        errorMessage?.let {
//            Text(text = it, color = MaterialTheme.colorScheme.error)
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Text(
//            text = "¿Qué tipo de lugar es?",
//            fontSize = 20.sp,
//            modifier = Modifier.padding(vertical = 8.dp)
//        )
//
//        Column {
//            categorias.forEach { categoria ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 4.dp)
//                ) {
//                    val isSelected = selectedCategory == categoria
//                    OutlinedButton(
//                        onClick = { viewModel.onCategorySelected(categoria) },
//                        colors = ButtonDefaults.outlinedButtonColors(
//                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
//                        ),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(
//                            text = categoria,
//                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
//                        )
//                    }
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            OutlinedButton(onClick = { onBack() }) {
//                Text("Atrás")
//            }
//
//            Button(
//                onClick = {
//                    if (locationName.isBlank()) {
//                        errorMessage = "Introduce una ubicación"
//                        return@Button
//                    }
//
//                    if (selectedCategory.isBlank()) {
//                        errorMessage = "Selecciona una categoría"
//                        return@Button
//                    }
//
//                    viewModel.geocodeLocation(
//                        context = context,
//                        onSuccess = { onNext() },
//                        onError = { error -> errorMessage = error }
//                    )
//                }
//            ) {
//                Text("Siguiente")
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearRecomendacion3(
    viewModel: RecomendacionViewModel = viewModel(),
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val locationName by viewModel.locationName
    val selectedCategory by viewModel.category
    val context = LocalContext.current

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val categorias = listOf("Comida", "Naturaleza", "Arte", "Cultura", "Otro")

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo con imagen y opacidad
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.5f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¿Dónde está este lugar?",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = locationName,
                onValueChange = { viewModel.onLocationChanged(it) },
                placeholder = { Text("Ej: Parque del Retiro", color = Color(0xFFBDBDBD)) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¿Qué tipo de lugar es?",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                categorias.forEach { categoria ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .background(
                                color = if (selectedCategory == categoria) Color(0xFFE3F2FD) else Color.White,
                                shape = RoundedCornerShape(50)
                            )
                            .clickable { viewModel.onCategorySelected(categoria) }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = categoria,
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GradientButton3(text = "Atrás") { onBack() }

                GradientButton3(text = "Siguiente") {
                    if (locationName.isBlank()) {
                        errorMessage = "Introduce una ubicación"
                        return@GradientButton3
                    }

                    if (selectedCategory.isBlank()) {
                        errorMessage = "Selecciona una categoría"
                        return@GradientButton3
                    }

                    viewModel.geocodeLocation(
                        context = context,
                        onSuccess = { onNext() },
                        onError = { error -> errorMessage = error }
                    )
                }
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it, color = Color.Red)
            }
        }
    }
}


@Composable
fun GradientButton3(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .widthIn(min = 120.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF5198ED),
                        Color(0xFF346298)
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

