package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.services.storage.file.PropertyFile.Column
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import androidx.compose.runtime.*



//vista para el titulo de la recomendacion - vista1
@Composable
fun CrearRecomendacion1(
    viewModel: RecomendacionViewModel = viewModel(),
    onNext: () -> Unit
){
    val title by viewModel.title
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¿Cómo se llama este lugar especial?",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = title,
            onValueChange = { newValue -> viewModel.onTitleChanged(newValue) },
            placeholder = { Text(text = "Ej: Café Luna") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            //no llamamos al metodo de crearRecomendacion hasta el final con todos los campso ya rellenos :) - sirve como MVP jeje
            onClick = {
                if (title.isNotBlank()) {
                    onNext()
                } else {
                    errorMessage = "El título no puede estar vacío"
                    Log.e("UI", "❌ El título no puede estar vacío")
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Siguiente")
        }
    }
}