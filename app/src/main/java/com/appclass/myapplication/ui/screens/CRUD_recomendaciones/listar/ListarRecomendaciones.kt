package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.listar

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.appclass.myapplication.data.recomendaciones.UserRecomendation
import com.appclass.myapplication.navigation.AppScreens
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import com.google.gson.Gson

@Composable
fun ListarRecomendaciones(
    navController: NavController,
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
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(text = rec.title, fontSize = 18.sp)
//                        Text(text = rec.category, style = MaterialTheme.typography.bodySmall)
//                        Text(text = rec.locationName, style = MaterialTheme.typography.bodySmall)
//
//                        Spacer(modifier = Modifier.height(8.dp))
//
//                        OutlinedButton(
//                            onClick = {
//                                viewModel.eliminarRecomendacion(
//                                    id = rec.id,
//                                    onSuccess = {
//                                        viewModel.listarRecomendacion {
//                                            Log.e("Firestore", "🔁 Error al recargar: ${it.message}")
//                                        }
//                                    },
//                                    onError = {
//                                        Log.e("Firestore", "❌ Error al eliminar: ${it.message}")
//                                    }
//                                )
//                            }
//                        ) {
//                            Text("Eliminar")
//                        }
//                    }
//                }
                RecomendacionCard(rec = rec, viewModel = viewModel, navController)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = onBack) {
            Text("Volver")
        }
    }
}



@Composable
fun RecomendacionCard(
    rec: UserRecomendation,
    viewModel: RecomendacionViewModel,
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¿Eliminar recomendación?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    viewModel.eliminarRecomendacion(
                        id = rec.id,
                        onSuccess = {
                            viewModel.listarRecomendacion {
                                Log.e("Firestore", "🔁 Error al recargar: ${it.message}")
                            }
                        },
                        onError = {
                            Log.e("Firestore", "❌ Error al eliminar: ${it.message}")
                        }
                    )
                }) {
                    Text("Sí, eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

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

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                OutlinedButton(
                    onClick = {
                        val json = Uri.encode(Gson().toJson(rec))
                        navController.navigate(AppScreens.EditarRecomendaciones.createRoute(json))
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Editar")
                }

                OutlinedButton(onClick = { showDialog = true }) {
                    Text("Eliminar")
                }
            }
        }
    }
}
