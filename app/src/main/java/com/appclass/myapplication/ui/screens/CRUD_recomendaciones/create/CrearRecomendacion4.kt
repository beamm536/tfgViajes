package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.services.storage.file.PropertyFile.Column
import com.appclass.myapplication.navigation.AppScreens
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CrearRecomendacion4 (
    viewModel: RecomendacionViewModel = viewModel(),
    onDone: () -> Unit,
    onBack: () -> Unit
){

    val isPublic by viewModel.isPublic
    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "¿Quién puede ver esta recomendación?",
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = isPublic,
                    onCheckedChange = { viewModel.onVisibilityChanged(it) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isPublic) "Pública" else "Privada")
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        Column {
            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Atrás")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    isSaving = true
                    errorMessage = null

                    if(FirebaseAuth.getInstance().currentUser == null){
                        errorMessage = "DEBES INICIAR SESION PARA GUARDAR UNA RECOMENDACION"
                        isSaving = false
                        return@Button
                    }

                    viewModel.createRecommendation(
                        onSuccess = {
                            isSaving = false
                            onDone()
                        },
                        onError = {
                            isSaving = false
                            errorMessage = it.message ?: "Error al guardar"
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isSaving) "Guardando..." else "Guardar recomendación")
            }
        }
    }
}