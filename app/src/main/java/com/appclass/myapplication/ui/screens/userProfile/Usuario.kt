package com.appclass.myapplication.ui.screens.userProfile

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.appclass.myapplication.R
import com.appclass.myapplication.navigation.AppScreens
import com.appclass.myapplication.ui.screens.registro.FuncionesRegistro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Usuario(navController: NavController, viewModel: UsuarioViewModel){

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
            CenterAlignedTopAppBar(title = { Text(text = "nombre del usuario") }, navigationIcon = {
                    IconButton(onClick = { /* TODO: manejar navegación hacia atrás */ }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Volver atrás"
                        )
                    }
                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFF00FF00) // Color de fondo
//                )
            )

            //Text(text = "nombre del usuario") //todo: mostrar el nombre del usuario con el q ha sido logeado

            //las llamadas al resto de funciones las haremos en FuncionesLogin
            FuncionesPerfilUsuario(
                viewModel = viewModel,
                navController
            )
        }
    }
}

@Composable
fun AvatarPerfilUsuario(modifier: Modifier, navController: NavController){


    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }

    Spacer(modifier = Modifier.height(10.dp))


    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.8f),
        shape = RoundedCornerShape(16.dp),
        //elevation = elevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center


        ) {
            Text(
                text = "Usuario Logueado",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Ubicación")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Madrid")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Sports ⚾ & Fortnite 🎮", color = Color.Gray)

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { navController.navigate(AppScreens.EditarPerfil.ruta) },
                colors = ButtonDefaults.buttonColors(Color(0xFF4A90E2))
            ) {
                Text(text = "Editar perfil", color = Color.White)
            }
        }
    }
}


@Composable
fun FuncionesPerfilUsuario(viewModel: UsuarioViewModel, navController: NavController){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6F7))
            .systemBarsPadding(), //para q el contenido no quede debajo de la barra de navegación
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        AvatarPerfilUsuario(modifier = Modifier, navController)
    }




}