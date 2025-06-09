package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.listar

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.appclass.myapplication.data.recomendaciones.UserRecomendation
import com.appclass.myapplication.navigation.AppScreens
import com.appclass.myapplication.ui.components.barraNavegacion.BottomNavBar
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import com.appclass.myapplication.ui.theme.Poppins
import com.google.gson.Gson
import com.appclass.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(110.dp),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "Tus recomendaciones",
                            style = MaterialTheme.typography.headlineSmall,
                            fontFamily = Poppins,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Unspecified
                )
            )
        },
        containerColor = Color(0xFFF0FAF6),
        bottomBar = {
            BottomNavBar(navController = navController, viewModel = viewModel())
        }
    ) { innerPadding ->


        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            //Text("Tus recomendaciones", fontSize = 22.sp)

            Spacer(modifier = Modifier.height(16.dp))

            if (recommendations.isEmpty()) {
                // Mostrar imagen y mensaje cuando no hay recomendaciones
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.no_result), // Usa tu imagen
                        contentDescription = "Sin recomendaciones",
                        modifier = Modifier.size(200.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No tienes recomendaciones aún.",
                        fontSize = 18.sp,
                        fontFamily = Poppins
                    )
                }
            } else {


                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(recommendations) { rec ->
                        RecomendacionCard(rec = rec, viewModel = viewModel, navController)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

//            OutlinedButton(onClick = onBack) {
//                Text("Volver")
//            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecomendacionCard(
    rec: UserRecomendation,
    viewModel: RecomendacionViewModel,
    navController: NavController
) {
    val lightBlue = Color(0xFFAEE6F4)
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
Column (
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {


    Card(

        modifier = Modifier
            //.fillMaxWidth()
            .padding(vertical = 8.dp)
            .width(320.dp)
            //.padding(horizontal = 16.dp)
            //.fillMaxWidth(0.95f)
            .clickable {
                val json = Uri.encode(Gson().toJson(rec))
                navController.navigate(
                    AppScreens.DetalleRecomendacionesPersonalizadas.createRoute(
                        json
                    )
                )
            },
        //shape = MaterialTheme.shapes.large,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = lightBlue)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = rec.title,
                fontSize = 20.sp,
                modifier = Modifier
                    .clickable {
                        val json = Uri.encode(Gson().toJson(rec))
                        navController.navigate(
                            AppScreens.DetalleRecomendacionesPersonalizadas.createRoute(
                                json
                            )
                        )
                    },
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            //Text(text = rec.category, style = MaterialTheme.typography.bodySmall)
            Text(text = rec.locationName, style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(12.dp))

//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
//            ) {
//                OutlinedButton(
//                    onClick = {
//                        val json = Uri.encode(Gson().toJson(rec))
//                        navController.navigate(AppScreens.EditarRecomendaciones.createRoute(json))
//                    },
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(end = 8.dp)
//                ) {
//                    Text("Editar")
//                }
//
//                OutlinedButton(
//                    onClick = { showDialog = true },
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Text("Eliminar")
//                }
//
//                Spacer(modifier = Modifier.weight(0.1f))
//
//            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        val json = Uri.encode(Gson().toJson(rec))
                        navController.navigate(AppScreens.EditarRecomendaciones.createRoute(json))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar recomendación",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = { showDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar recomendación",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

        }
    }
}
}
