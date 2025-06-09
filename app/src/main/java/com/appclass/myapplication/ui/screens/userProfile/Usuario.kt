package com.appclass.myapplication.ui.screens.userProfile

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.appclass.myapplication.R
import com.appclass.myapplication.models.Favorito
import com.appclass.myapplication.models.User
import com.appclass.myapplication.navigation.AppScreens
import com.appclass.myapplication.ui.components.barraNavegacion.BottomNavBar
import com.appclass.myapplication.ui.components.barraNavegacion.NavigationViewModel
import com.appclass.myapplication.ui.screens.registro.FuncionesRegistro
import com.appclass.myapplication.ui.theme.Poppins
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Usuario(navController: NavHostController, viewModel: UsuarioViewModel){

    //DECLARACION DE LAS VARIABLES DE ESETADO DEL VIEWMODEL
    val navigationViewModel: NavigationViewModel = viewModel()
    //val favoritos by viewModel.favoritos.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(110.dp),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Mi Perfil",
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Bold
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
            BottomNavBar(navController = navController, viewModel = navigationViewModel)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF0FAF6)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFF00FF00) // Color de fondo
//                )


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
fun AvatarPerfilUsuario(user: User?,modifier: Modifier, navController: NavController, nombreUsuario: String){

    val genero = user?.genero?.lowercase() ?: ""
    val imgAvatar = when (genero) {
        "masculino" -> R.drawable.male_avatar
        "femenino" -> R.drawable.female_avatar
        "otro" -> R.drawable.other_avatar
        else -> R.drawable.other_avatar
    }

    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imgAvatar),
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
                text = nombreUsuario,
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
            //Text(text = "Sports ⚾ & Fortnite 🎮", color = Color.Gray)

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

    val user by viewModel.usuario.collectAsState()
    val nombreUsuario by viewModel.nombreUsuario.collectAsState()
    val favoritos by viewModel.favoritos.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0FAF6))
            .systemBarsPadding(), //para q el contenido no quede debajo de la barra de navegación
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        AvatarPerfilUsuario(user = user, modifier = Modifier, navController, nombreUsuario = nombreUsuario)


//        if (favoritos.isNotEmpty()) {
//            Spacer(modifier = Modifier.height(24.dp))
//            Text("Lugares guardados", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//
//            favoritos.forEach { fav ->
//                FavoritoCard(favorito = fav)
//            }
//        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Tus Favoritos ❤\uFE0F",
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier.weight(1f)
            )
        }

        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items (favoritos) { favorito ->
                FavoritoCard(favorito = favorito, onDeleteConfirm = {
                    viewModel.eliminarFavorito(favorito)
                })
            }
        }
    }





}


//@Composable
//fun FavoritoCard(favorito: Favorito) {
//
//    //formato legible de la fecha de guardado / marcado a favoritos
//    val fechaLegible = remember(favorito.fechaGuardado) {
//        val date = favorito.fechaGuardado.toDate()  // 🔁 Timestamp -> Date
//        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(date)
//    }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        shape = RoundedCornerShape(12.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = favorito.nombreLugar, fontWeight = FontWeight.Bold)
//            Text(text = favorito.ubicacion)
//            Text(text = favorito.descripcion, maxLines = 2)
//            Text(text = "Guardado: ${fechaLegible}", fontSize = 12.sp, color = Color.Gray)
//        }
//    }
//}

@Composable
fun FavoritoCard(favorito: Favorito, onDeleteConfirm: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¿Eliminar favorito?") },
            text = { Text("¿Estás seguro de que quieres quitar este lugar de favoritos?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteConfirm()
                    showDialog = false
                }) {
                    Text("Sí")
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
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = favorito.nombreLugar, fontWeight = FontWeight.Bold)
            Text(text = favorito.ubicacion)
            favorito.descripcion?.let {
                Text(text = it, maxLines = 2)
            }
            Text(text = "Guardado: ${favorito.fechaGuardado.toDate().toLocaleString()}", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
//            Button(
//                onClick = { showDialog = true },
//                colors = ButtonDefaults.buttonColors(Color.Red)
//            ) {
//                Text("Quitar de favoritos", color = Color.White)
//            }
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Quitar de favoritos",
                    tint = Color.Red
                )
            }
        }
    }
}
