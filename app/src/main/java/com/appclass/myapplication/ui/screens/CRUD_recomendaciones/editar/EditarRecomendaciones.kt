package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.editar

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.appclass.myapplication.R
import com.appclass.myapplication.data.recomendaciones.UserRecomendation
import com.appclass.myapplication.ui.components.barraNavegacion.BottomNavBar
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import com.appclass.myapplication.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarRecomendaciones(
    navController: NavController,
    recomendacion: UserRecomendation,
    viewModel: RecomendacionViewModel = viewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var titulo by remember { mutableStateOf(recomendacion.title) }
    var descripcion by remember { mutableStateOf(recomendacion.description) }
    var locationName by remember { mutableStateOf(recomendacion.locationName) }
    var categoria by remember { mutableStateOf(recomendacion.category) }

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
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "volver",
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clickable { onBack() }
                        )

                        Spacer(Modifier.size(24.dp))

                        Text(
                            text = "Editar Recomendación",
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

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            //Text("Editar recomendación", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier
//                    .fillMaxWidth(0.9f)
//                    .height(150.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color(0xFFD4F0F8), shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(Modifier.size(16.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth(0.68f)
                    .height(150.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color(0xFFD4F0F8), shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(Modifier.size(16.dp))

            OutlinedTextField(
                value = locationName,
                onValueChange = { locationName = it },
                label = { Text("Ubicación") },
                modifier = Modifier
//                    .fillMaxWidth(0.9f)
//                    .height(150.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color(0xFFD4F0F8), shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(Modifier.size(16.dp))

            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría") },
                modifier = Modifier
//                    .fillMaxWidth(0.9f)
//                    .height(150.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color(0xFFD4F0F8), shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
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
}