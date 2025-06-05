package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.listar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appclass.myapplication.data.recomendaciones.UserRecomendation

@Composable
fun DetalleRecomendacionesPersonalizadas(
    recomendacion: UserRecomendation,
    onBack: () -> Unit
){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = recomendacion.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Categoría: ${recomendacion.category}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Ubicación: ${recomendacion.locationName}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Descripción:")
        Text(text = recomendacion.description)

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack) {
            Text("Volver")
        }
    }
}