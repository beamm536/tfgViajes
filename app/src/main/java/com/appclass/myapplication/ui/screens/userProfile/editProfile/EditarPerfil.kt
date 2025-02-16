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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            CenterAlignedTopAppBar(
                title = { Text(text = "Editar Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {//volver de la pantalla de la q el usuario viene
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Volver atrás"
                        )
                    }
                }
            )
            FuncionesEditarPerfilUsuario(
                viewModel = viewModel,
                navController
            )
        }
    }
}

@Composable
fun AvatarNombreUsuario(){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvasusuario()

        Spacer(modifier = Modifier.size(24.dp))

        // Nombre y Ubicación
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "Preston Cooper \n Nienow",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray, modifier = Modifier.size(20.dp))
            Text(text = "📍 Madrid, Spain", fontSize = 14.sp, color = Color.Gray)
        }
    }

}


@Composable
fun EditProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar
//        Box(
//            modifier = Modifier
//                .size(100.dp)
//                .clip(CircleShape)
//                .background(Color.LightGray),
//            contentAlignment = Alignment.BottomEnd
//        ) {
//            Icon(Icons.Default.AccountCircle, contentDescription = "Avatar", modifier = Modifier.size(100.dp))
//            Icon(
//                Icons.Default.KeyboardArrowLeft,
//                contentDescription = "Editar Foto",
//                tint = Color.Blue,
//                modifier = Modifier
//                    .size(24.dp)
//                    .background(Color.White, shape = CircleShape)
//                    .padding(4.dp)
//            )
//        }




        Spacer(modifier = Modifier.height(16.dp))

        // Campos de entrada
        ProfileTextField(label = "Nombre de usuario*", placeholder = "Ejemplo123")
        ProfileTextField(label = "Presentación", placeholder = "Ej: Sports 🏀 & Fortnite 🎮")
        ProfileTextField(label = "Fecha de nacimiento*", placeholder = "13-03-1972",/* trailingIcon = Icons.Default.DateRange*/)
        ProfileDropdown(label = "Seleccione un género")

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Guardar Cambios
        Button(
            onClick = { /* Guardar cambios */ },
            colors = ButtonDefaults.buttonColors(Color(0xFF4A90E2)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Cambios", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Configuración y eliminar cuenta
        TextButton(onClick = { /* Ir a configuración */ }) {
            Text(text = "Configuración de la cuenta", color = Color(0xFF4A90E2))
        }

        TextButton(onClick = { /* Eliminar cuenta */ }) {
            Text(text = "Eliminar cuenta", color = Color.Red)
        }
    }
}

@Composable
fun ProfileDropdown(label: String) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

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
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, Modifier.clickable { expanded = true })
                }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("Masculino") }, onClick = { selectedText = "Masculino"; expanded = false })
                DropdownMenuItem(text = { Text("Femenino") }, onClick = { selectedText = "Femenino"; expanded = false })
                DropdownMenuItem(text = { Text("Otro") }, onClick = { selectedText = "Otro"; expanded = false })
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

//TODO --> tendremos q hacer como una "inyeccion" de los valores para no repetir ==> IGUAL Q REACT CON ${props.email}

@Composable
    fun ProfileTextField(label: String, placeholder: String, trailingIcon: ImageVector? = null) {
        Column {
            Text(text = label, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = "email",
                onValueChange ={ /*"onEmailChanged"(it)*/ }, //es lo mismo que onTextFieldChanged(email, it) -> pero para q no de error con los parámetros
                label = { Text("Email") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }


@Composable
fun Canvasusuario(){
    Canvas(modifier = Modifier.size(120.dp, 150.dp)) {
        drawRoundRect(
            color = Color.Gray,
            size = Size(size.width, size.height),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(100f, 100f)
        )
    }
}



@Composable
fun FuncionesEditarPerfilUsuario(viewModel: EditarPerfilViewModel, navController: NavController){
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6F7))
            .systemBarsPadding(), //para q el contenido no quede debajo de la barra de navegación
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        item { AvatarNombreUsuario() }
        item { EditProfileScreen(navController) }
        //EditProfileScreen(navController)
    }
}