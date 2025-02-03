package com.appclass.myapplication.ui.screens.userProfile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.appclass.myapplication.ui.screens.registro.FuncionesRegistro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Usuario(viewModel: UsuarioViewModel){

    //DECLARACION DE LAS VARIABLES DE ESETADO DEL VIEWMODEL


    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = { Text(text = "Perfil de Usuario") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: manejar navegación hacia atrás */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                }
            )
            Text(text = "nombre del usuario") //todo: mostrar el nombre del usuario con el q ha sido logeado

            //las llamadas al resto de funciones las haremos en FuncionesLogin
            FuncionesPerfilUsuario(
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun FuncionesPerfilUsuario(viewModel: UsuarioViewModel){

}