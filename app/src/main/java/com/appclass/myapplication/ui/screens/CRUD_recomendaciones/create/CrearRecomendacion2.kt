package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.appclass.myapplication.R

//vista para la descripcion de la recomendacion - vista2
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearRecomendacion2 (
    viewModel: RecomendacionViewModel = viewModel(),
    onNext: () -> Unit,
    onBack: () -> Unit
){
    val description by viewModel.description

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo con opacidad del 50%
        Image(
            painter = painterResource(id = R.drawable.fondo), // Asegúrate de que este nombre coincida
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.5f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Descríbelo en pocas palabras",
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

//            TextField(
//                value = description,
//                onValueChange = { viewModel.onDescriptionChanged(it) },
//                placeholder = { Text("Ej: Lugar tranquilo para tomar café...") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp)
//            )
            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.onDescriptionChanged(it) },
                placeholder = {
                    Text(
                        "Ej. “Lorem ipsumLorem ipsumLorem ipsumLorem ipsumLorem ipsum”",
                        color = Color(0xFFBDBDBD)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(150.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                OutlinedButton(onClick = { onBack() }) {
//                    Text("Atrás")
//                }
//
//                Button(
//                    modifier = Modifier
//                        //.align(Alignment.CenterHorizontally)
//                        .height(48.dp)
//                        .widthIn(min = 120.dp)
//                        .background(
//                            brush = Brush.verticalGradient(
//                                colors = listOf(
//                                    Color(0xFF5198ED), // color superior pra el degradado
//                                    Color(0xFF346298)  // color inferior - degradado
//                                )
//                            ),
//                            shape = RoundedCornerShape(24.dp)
//                        ),
//                    onClick = {
                    GradientButton2(text = "Atrás") { onBack() }
                    GradientButton2(text = "Siguiente") {
                        if (description.isNotBlank()) {
                            onNext()
                        } else {
                            errorMessage = "La descripción no puede estar vacía"
                            Log.e("UI", "❌ La descripción no puede estar vacía")
                        }
                    }
//                ) {
//                    Text("Siguiente")
//                }
            }
        }
    }
}


@Composable
fun GradientButton2(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .widthIn(min = 120.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF5198ED),
                        Color(0xFF346298)
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

