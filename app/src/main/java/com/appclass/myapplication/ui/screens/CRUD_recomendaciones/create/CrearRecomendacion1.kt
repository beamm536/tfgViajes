package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.services.storage.file.PropertyFile.Column
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.appclass.myapplication.R

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
//import androidx.compose.ui.Alignment


//vista para el titulo de la recomendacion - vista1
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearRecomendacion1(
    viewModel: RecomendacionViewModel = viewModel(),
    onNext: () -> Unit
){

    LaunchedEffect(Unit) {
        viewModel.resetCampos()  //reinicio de los campos
    }


    val title by viewModel.title
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
                text = "¿Cómo se llama este lugar especial?",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

//            TextField(
//                value = title,
//                onValueChange = { newValue -> viewModel.onTitleChanged(newValue) },
//                placeholder = { Text(text = "Ej: Café Luna") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.White, shape = RoundedCornerShape(12.dp))
//            )
            OutlinedTextField(
                value = title,
                onValueChange = { newValue -> viewModel.onTitleChanged(newValue) },
                placeholder = { Text("Ej: Parque del Retiro", color = Color(0xFFBDBDBD)) },
                modifier = Modifier
//                    .fillMaxWidth()
//                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.White),
                shape = RoundedCornerShape(16.dp),
                colors = outlinedTextFieldColors(
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    focusedPlaceholderColor = Color(0XFFBDBDBD)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

//            Button(
//                //no llamamos al metodo de crearRecomendacion hasta el final con todos los campso ya rellenos :) - sirve como MVP jeje
//                onClick = {
//                    if (title.isNotBlank()) {
//                        onNext()
//                    } else {
//                        errorMessage = "El título no puede estar vacío"
//                        Log.e("UI", "❌ El título no puede estar vacío")
//                    }
//                },
//                modifier = Modifier
//                    .align(Alignment.End)
//                    .height(48.dp),
//                shape = RoundedCornerShape(24.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFF3C83F6), // color azul similar
//                    contentColor = Color.White
//                )
//            ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(48.dp)
                    .widthIn(min = 120.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF5198ED), // color superior pra el degradado
                                Color(0xFF346298)  // color inferior - degradado
                            )
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 24.dp)
                    .clickable {
                        if (title.isNotBlank()) {
                            onNext()
                        } else {
                            errorMessage = "El título no puede estar vacío"
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Siguiente",
                    color = Color.White
                )

            }
        }
    }
}