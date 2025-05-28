package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

//vista para la localizacion de la recomendacion - vista3
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CrearRecomendacion3 (
    viewModel: RecomendacionViewModel = viewModel(),
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val locationName by viewModel.locationName
    val selectedCategory by viewModel.category
    val context = LocalContext.current

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val categorias = listOf("Comida", "Naturaleza", "Arte", "Cultura", "Vida Nocturna", "Otro")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "¿Dónde está este lugar?",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = locationName,
            onValueChange = { viewModel.onLocationChanged(it) },
            placeholder = { Text("Ej: Barcelona, España") },
            modifier = Modifier.fillMaxWidth()
        )

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¿Qué tipo de lugar es?",
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Column {
            categorias.forEach { categoria ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    val isSelected = selectedCategory == categoria
                    OutlinedButton(
                        onClick = { viewModel.onCategorySelected(categoria) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = categoria,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = { onBack() }) {
                Text("Atrás")
            }

            Button(
                onClick = {
                    if (locationName.isBlank()) {
                        errorMessage = "Introduce una ubicación"
                        return@Button
                    }

                    if (selectedCategory.isBlank()) {
                        errorMessage = "Selecciona una categoría"
                        return@Button
                    }

                    viewModel.geocodeLocation(
                        context = context,
                        onSuccess = { onNext() },
                        onError = { error -> errorMessage = error }
                    )
                }
            ) {
                Text("Siguiente")
            }
        }
    }
}
