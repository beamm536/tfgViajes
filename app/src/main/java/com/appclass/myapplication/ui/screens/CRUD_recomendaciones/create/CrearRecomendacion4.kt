package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.test.services.storage.file.PropertyFile.Column
import com.appclass.myapplication.R
import com.appclass.myapplication.navigation.AppScreens
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CrearRecomendacion4 (
    navController: NavController,
    viewModel: RecomendacionViewModel = viewModel(),
    onDone: () -> Unit,
    onBack: () -> Unit
){

    val isPublic by viewModel.isPublic
    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

//    Box(modifier = Modifier.fillMaxSize()) {
//        // Fondo con imagen y opacidad
//        Image(
//            painter = painterResource(id = R.drawable.fondo),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxSize()
//                .graphicsLayer(alpha = 0.5f)
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(24.dp),
//            verticalArrangement = Arrangement.SpaceBetween,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Column {
//                Text(
//                    text = "¿Quién puede ver esta recomendación?",
//                    fontSize = 22.sp,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//
//                    Image(
//                        painter = painterResource(
//                            id = if (isPublic)
//                                R.drawable.public_blue
//                            else
//                                R.drawable.private_blue
//                        ),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(150.dp)
//                            .padding(bottom = 16.dp)
//                    )
//
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Switch(
//                        checked = isPublic,
//                        onCheckedChange = { viewModel.onVisibilityChanged(it) }
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(if (isPublic) "Pública" else "Privada")
//                }
//
//                errorMessage?.let {
//                    Text(
//                        text = it,
//                        color = MaterialTheme.colorScheme.error,
//                        modifier = Modifier.padding(top = 16.dp)
//                    )
//                }
//            }
//
//            Column {
//                OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
//                    Text("Atrás")
//                }
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                Button(
//                    onClick = {
//                        isSaving = true
//                        errorMessage = null
//
//                        //                    if(FirebaseAuth.getInstance().currentUser == null){
//                        //                        errorMessage = "DEBES INICIAR SESION PARA GUARDAR UNA RECOMENDACION"
//                        //                        isSaving = false
//                        //                        return@Button
//                        //                    }
//
//                        viewModel.createRecommendation(
//                            onSuccess = {
//                                isSaving = false
//                                onDone()
//                            },
//                            onError = {
//                                isSaving = false
//                                errorMessage = it.message ?: "Error al guardar"
//                            }
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(if (isSaving) "Guardando..." else "Guardar recomendación")
//                }
//            }
//        }
//    }
//    Box(modifier = Modifier.fillMaxSize()) {
//        // Fondo
//        Image(
//            painter = painterResource(id = R.drawable.fondo),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxSize()
//                .graphicsLayer(alpha = 0.5f)
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 24.dp, vertical = 32.dp),
//            verticalArrangement = Arrangement.SpaceBetween,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(
//                    text = "¿Quién puede ver esta recomendación?",
//                    fontSize = 20.sp,
//                    color = Color.Black,
//                    modifier = Modifier.padding(bottom = 24.dp)
//                )
//
//                // Imagen decorativa
//                Image(
//                    painter = painterResource(
//                        id = if (isPublic)
//                            R.drawable.public_blue
//                        else
//                            R.drawable.private_blue
//                    ),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(200.dp)
//                        .padding(bottom = 24.dp)
//                )
//
//                // Switch y estado
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp)
//                ) {
//                    Switch(
//                        checked = isPublic,
//                        onCheckedChange = { viewModel.onVisibilityChanged(it) }
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        if (isPublic) "Pública" else "Privada",
//                        fontSize = 16.sp,
//                        color = Color.Black
//                    )
//                }
//
//                // Error
//                errorMessage?.let {
//                    Text(
//                        text = it,
//                        color = MaterialTheme.colorScheme.error,
//                        modifier = Modifier.padding(top = 16.dp)
//                    )
//                }
//            }
//
//            // Botones
//            Column {
//                OutlinedButton(
//                    onClick = onBack,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(48.dp),
//                    shape = MaterialTheme.shapes.medium
//                ) {
//                    Text("Atrás")
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Button(
//                    onClick = {
//                        isSaving = true
//                        errorMessage = null
//
//                        viewModel.createRecommendation(
//                            onSuccess = {
//                                isSaving = false
//                                onDone()
//                            },
//                            onError = {
//                                isSaving = false
//                                errorMessage = it.message ?: "Error al guardar"
//                            }
//                        )
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(48.dp),
//                    shape = MaterialTheme.shapes.medium
//                ) {
//                    Text(if (isSaving) "Guardando..." else "Guardar recomendación")
//                }
//            }
//        }
//    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.5f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "¿Quién puede ver esta recomendación?",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Puedes elegir si tu recomendación será visible para todos o solo para ti.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(bottom = 24.dp),
                )

                // Imagen con animación al cambiar
                val imageRes = if (isPublic) R.drawable.public_blue else R.drawable.private_blue
                Crossfade(targetState = imageRes) { resId ->
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 24.dp)
                    )
                }

                // Switch + estado
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Switch(
                        checked = isPublic,
                        onCheckedChange = { viewModel.onVisibilityChanged(it) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (isPublic) "Pública" else "Privada",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }

                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            // Botones
            Column {
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text("Atrás")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        isSaving = true
                        errorMessage = null

                        viewModel.createRecommendation(
                            onSuccess = {
                                viewModel.listarRecomendacion {  } //forzamos la recarga
                                isSaving = false
                                onDone()
                            },
                            onError = {
                                isSaving = false
                                errorMessage = it.message ?: "Error al guardar"
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(if (isSaving) "Guardando..." else "Guardar recomendación")
                }
            }
        }
    }
}