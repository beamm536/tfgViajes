package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*

//vista para la descripcion de la recomendacion - vista2
@Composable
fun CrearRecomendacion2 (
    viewModel: RecomendacionViewModel = viewModel(),
    onNext: () -> Unit,
    onBack: () -> Unit
){
    val description by viewModel.description

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Descríbelo en pocas palabras",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = description,
            onValueChange = { viewModel.onDescriptionChanged(it) },
            placeholder = { Text("Ej: Lugar tranquilo para tomar café...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = { onBack() }) {
                Text("Atrás")
            }

            Button(
                onClick = {

                    if (description.isNotBlank()) {
                        onNext()
                    } else {
                        errorMessage = "La descripción no puede estar vacía"
                        Log.e("UI", "❌ La descripción no puede estar vacía")
                    }
                }
            ) {
                Text("Siguiente")
            }
        }
    }
}

