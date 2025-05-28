package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.listar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel

@Composable
fun ListarRecomendaciones(
    viewModel: RecomendacionViewModel = viewModel(),
    onBack: () -> Unit
){
    val recommendations = viewModel.userRecommendations

    LaunchedEffect(Unit) {
        viewModel.listarRecomendacion {
            Log.e("Firestore", "❌ Error al cargar: ${it.message}")
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tus recomendaciones", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(recommendations) { rec ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = rec.title, fontSize = 18.sp)
                        Text(text = rec.category, style = MaterialTheme.typography.bodySmall)
                        Text(text = rec.locationName, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = onBack) {
            Text("Volver")
        }
    }
}