package com.appclass.myapplication.ui.screens.userProfile.editProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.appclass.myapplication.ui.screens.userProfile.FuncionesPerfilUsuario
import com.appclass.myapplication.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfil(navController: NavController, viewModel: EditarPerfilViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenterAlignedTopAppBar(title = { Text(text = "editar perfil") }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Volver atrás"
                    )
                }
            }
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFF00FF00) // Color de fondo
//                )
            )

            //Text(text = "nombre del usuario") //todo: mostrar el nombre del usuario con el q ha sido logeado

            //las llamadas al resto de funciones las haremos en FuncionesLogin
//            FuncionesPerfilUsuario(
//                viewModel = viewModel,
//                navController
//            )
        }
    }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "Editar Perfil", fontFamily = Poppins, fontWeight = FontWeight.Bold)
//        Button(onClick = { navController.popBackStack() }) {//volver de la pantalla de la q el usuario viene
//            Text("Volver")
//        }
//    }
}