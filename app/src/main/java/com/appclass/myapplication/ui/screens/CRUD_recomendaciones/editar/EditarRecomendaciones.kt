package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.editar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appclass.myapplication.data.recomendaciones.UserRecomendation
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel

@Composable
fun EditarRecomendaciones(
    recomendacion: UserRecomendation,
    viewModel: RecomendacionViewModel = viewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var titulo by remember { mutableStateOf(recomendacion.title) }
    var descripcion by remember { mutableStateOf(recomendacion.description) }
    var locationName by remember { mutableStateOf(recomendacion.locationName) }
    var categoria by remember { mutableStateOf(recomendacion.category) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Editar recomendación", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = locationName,
            onValueChange = { locationName = it },
            label = { Text("Ubicación") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val updated = recomendacion.copy(
                title = titulo,
                description = descripcion,
                locationName = locationName,
                category = categoria
            )

            viewModel.editarRecomendacion(
                recomendation = updated,
                onSuccess = {
                    Log.d("Firestore", "✅ Recomendación editada")
                    onBack()
                },
                onError = {
                    Log.e("Firestore", "❌ Error al editar: ${it.message}")
                }
            )
        }) {
            Text("Guardar cambios")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = onBack) {
            Text("Cancelar")
        }
    }
}