package com.appclass.myapplication.ui.screens.userProfile.editProfile

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.appclass.myapplication.models.User
import com.appclass.myapplication.ui.screens.userProfile.FuncionesPerfilUsuario
import com.appclass.myapplication.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfil(navController: NavController, viewModel: EditarPerfilViewModel) {

    LaunchedEffect(Unit) {
        viewModel.cargarDatosUsuario()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenterAlignedTopAppBar(
                title = { Text(text = "Editar Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Volver atrás")
                    }
                }
            )
            FuncionesEditarPerfilUsuario(viewModel, navController)
        }
    }
}

@Composable
fun FuncionesEditarPerfilUsuario(viewModel: EditarPerfilViewModel, navController: NavController) {

    val user by viewModel.usuario.observeAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6F7))
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { AvatarNombreUsuario(user) }
        item { EditProfileScreen(navController, user, viewModel) }
    }
}

@Composable
fun AvatarNombreUsuario(user: User?) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvasusuario()
        Spacer(modifier = Modifier.size(24.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "${user?.nombre ?: ""} ${user?.apellidos ?: ""}",
                fontSize = 20.sp
            )
            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray, modifier = Modifier.size(20.dp))
            Text(text = "📍 España", fontSize = 14.sp, color = Color.Gray) // o puedes guardar ciudad en Firestore si la tienes
        }
    }
}

@Composable
fun EditProfileScreen(navController: NavController, user: User?, viewModel: EditarPerfilViewModel) {

    val nombre = remember { mutableStateOf(user?.nombre ?: "") }
    val apellidos = remember { mutableStateOf(user?.apellidos ?: "") }
    val fechaNacimiento = remember { mutableStateOf(user?.fechaNacimiento ?: "") }
    val genero = remember { mutableStateOf(user?.genero ?: "") }
    val email = remember { mutableStateOf(user?.email ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ProfileTextField(label = "Nombre", value = nombre.value) { nombre.value = it }
        ProfileTextField(label = "Apellidos", value = apellidos.value) { apellidos.value = it }
        ProfileTextField(label = "Fecha Nacimiento", value = fechaNacimiento.value) { fechaNacimiento.value = it }
        ProfileDropdown(label = "Seleccione un género", initialValue = genero.value) { genero.value = it }
        ProfileTextField(label = "Email", value = email.value) { email.value = it }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val updatedUser = User(
                    uid = user?.uid ?: "",
                    nombre = nombre.value,
                    apellidos = apellidos.value,
                    fechaNacimiento = fechaNacimiento.value,
                    genero = genero.value,
                    email = email.value
                )
                viewModel.actualizarDatosUsuario(updatedUser) { success, error ->
                    if (success) {
                        println("Perfil actualizado correctamente")
                        // Muestra un mensaje o vuelve atrás si quieres
                    } else {
                        println("Error actualizando perfil: $error")
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF4A90E2)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Cambios", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { /* Configuración */ }) {
            Text(text = "Configuración de la cuenta", color = Color(0xFF4A90E2))
        }

        TextButton(onClick = { /* Eliminar cuenta */ }) {
            Text(text = "Eliminar cuenta", color = Color.Red)
        }
    }
}

@Composable
fun ProfileTextField(label: String, value: String, /*trailingIcon: ImageVector? = null*/onValueChange: (String) -> Unit) {
    Column {
        Text(text = label, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = value,
            onValueChange = {}, // Deja esto por ahora como solo lectura
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ProfileDropdown(label: String, initialValue: String = "", onValueChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(initialValue) }

    Column {
        Text(text = label, fontWeight = FontWeight.Bold)
        Box {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                placeholder = { Text("Seleccione una opción", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = true }
                    )
                }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("Masculino") }, onClick = {
                    selectedText = "Masculino"
                    expanded = false
                })
                DropdownMenuItem(text = { Text("Femenino") }, onClick = {
                    selectedText = "Femenino"
                    expanded = false
                })
                DropdownMenuItem(text = { Text("Otro") }, onClick = {
                    selectedText = "Otro"
                    expanded = false
                })
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun Canvasusuario() {
    Canvas(modifier = Modifier.size(120.dp, 150.dp)) {
        drawRoundRect(
            color = Color.Gray,
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(100f, 100f)
        )
    }
}