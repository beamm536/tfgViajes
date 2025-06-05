package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.listar

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
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
    val refreshTrigger = remember { mutableStateOf(0) }
    val recommendations = viewModel.userRecommendations

    LaunchedEffect(refreshTrigger.value/*Unit*/) {
        viewModel.listarRecomendacion {
            Log.e("Firestore", "❌ Error al cargar: ${it.message}")
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tus recomendaciones", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(recommendations) { rec ->
                RecomendacionCard(rec = rec, viewModel = viewModel, navController)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = onBack) {
            Text("Volver")
        }
    }
}



//@Composable
//fun RecomendacionCard(
//    rec: UserRecomendation,
//    viewModel: RecomendacionViewModel,
//    navController: NavController
//) {
//    var showDialog by remember { mutableStateOf(false) }
//
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = { showDialog = false },
//            title = { Text("¿Eliminar recomendación?") },
//            text = { Text("Esta acción no se puede deshacer.") },
//            confirmButton = {
//                TextButton(onClick = {
//                    showDialog = false
//                    viewModel.eliminarRecomendacion(
//                        id = rec.id,
//                        onSuccess = {
//                            viewModel.listarRecomendacion {
//                                Log.e("Firestore", "🔁 Error al recargar: ${it.message}")
//                            }
//                        },
//                        onError = {
//                            Log.e("Firestore", "❌ Error al eliminar: ${it.message}")
//                        }
//                    )
//                }) {
//                    Text("Sí, eliminar")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showDialog = false }) {
//                    Text("Cancelar")
//                }
//            }
//        )
//    }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .clickable {
//                val json = Uri.encode(Gson().toJson(rec))
//                navController.navigate(AppScreens.DetalleRecomendacionesPersonalizadas.createRoute(json))
//            },
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = rec.title,
//                fontSize = 18.sp,
//                modifier = Modifier
//                    .clickable{
//                        val json = Uri.encode(Gson().toJson(rec))
//                        /*
//                        GSON().TOJSON(REC)
//                        recibir la recomendacion en formato json para luego una mejor manipulación de los datos
//
//                        URI-ENCODE
//                        traduce esos caracteres a una forma segura para pasarlos por una ruta de navegación,
//                        para que no se rompa lal url si los mandamos directamente
//                         */
//                        navController.navigate(AppScreens.DetalleRecomendacionesPersonalizadas.createRoute(json))
//                    }
//
//            )
//            Text(text = rec.category, style = MaterialTheme.typography.bodySmall)
//            Text(text = rec.locationName, style = MaterialTheme.typography.bodySmall)
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row {
//                OutlinedButton(
//                    onClick = {
//                        val json = Uri.encode(Gson().toJson(rec))
//                        navController.navigate(AppScreens.EditarRecomendaciones.createRoute(json))
//                    },
//                    modifier = Modifier.padding(end = 8.dp)
//                ) {
//                    Text("Editar")
//                }
//
//                OutlinedButton(onClick = { showDialog = true }) {
//                    Text("Eliminar")
//                }
//            }
//        }
//    }
//}

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
            .padding(vertical = 8.dp)
            .clickable {
                val json = Uri.encode(Gson().toJson(rec))
                navController.navigate(AppScreens.DetalleRecomendacionesPersonalizadas.createRoute(json))
            },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = rec.title,
                fontSize = 20.sp,
                modifier = Modifier
                    .clickable {
                        val json = Uri.encode(Gson().toJson(rec))
                        navController.navigate(AppScreens.DetalleRecomendacionesPersonalizadas.createRoute(json))
                    },
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = rec.category, style = MaterialTheme.typography.bodySmall)
            Text(text = rec.locationName, style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        val json = Uri.encode(Gson().toJson(rec))
                        navController.navigate(AppScreens.EditarRecomendaciones.createRoute(json))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Editar")
                }

                OutlinedButton(
                    onClick = { showDialog = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar")
                }

                Spacer(modifier = Modifier.weight(0.1f))

//                Text(
//                    text = if (rec.isPublic) "🌐 Pública" else "🔒 Privada",
//                    fontSize = 12.sp,
//                    modifier = Modifier.padding(start = 8.dp),
//                    color = if (rec.isPublic) Color(0xFF5198ED) else Color(0xFF346298)
//                )
            }
        }
    }
}
