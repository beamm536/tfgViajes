package com.appclass.myapplication.ui.screens.registro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appclass.myapplication.R
import com.appclass.myapplication.ui.screens.cambioVistasSwitch.AuthViewModel
import com.appclass.myapplication.ui.theme.Poppins
import com.appclass.myapplication.ui.theme.txtBlack
import androidx.compose.material3.IconButton as IconButton1

@Composable
fun Registro(viewModel: RegistroViewModel, navigateToHome: () -> Unit, switcher: @Composable () -> Unit){

    //DECLARACION DE LAS VARIABLES DE ESETADO DEL VIEWMODEL
    val nombre: String by viewModel.nombre.observeAsState(initial = "")
    val apellidos: String by viewModel.apellidos.observeAsState(initial = "")
    val fechaNacimiento: String by viewModel.fechaNacimiento.observeAsState(initial = "")
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val registroEnable: Boolean by viewModel.registroEnable.observeAsState(initial = false) //nuestro boton empieza deshabilitado

    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Iniciar sesión")

            //las llamadas al resto de funciones las haremos en FuncionesLogin
            FuncionesRegistro(
                viewModel = viewModel,
                switcher = switcher,
                nombre = nombre,
                apellidos = apellidos,
                fechaNacimiento = fechaNacimiento,
                email = email,
                password = password,

                registroEnable = registroEnable,
                onLoginSuccess = navigateToHome
            )
        }
    }
}

@Composable
fun LogoAppRegistro(modifier: Modifier){
    Image(
        painter = painterResource(id = R.drawable.primerlogo_app_removebg_preview),
        contentDescription = "logoApp_pantallaRegistro",
        modifier = Modifier.size(75.dp, 75.dp)
    )
}
@Composable
fun TxtsInicioRegistro(){
    Text(
        text = "¡Create una cuenta!",
        fontSize = 32.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        color = txtBlack
    )
}
@Composable
fun CamposRegistro(
    authViewModel: AuthViewModel,
    nombre: String,
    onNombreChanged: (String) -> Unit,
    apellidos: String,
    onApellidosChanged: (String) -> Unit,
    fechaNacimiento: String,
    onFechaNacimientoChanged: (String) -> Unit,
    email: String,
    onEmailChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    registroEnable: Boolean
){

    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = nombre,
                onValueChange = { onNombreChanged(it) },
                label = { Text("Nombre") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = apellidos,
                onValueChange = { onApellidosChanged(it) },
                label = { Text("Apellidos") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = fechaNacimiento,
            onValueChange = { onFechaNacimientoChanged(it) },
            label = { Text("Birth of date (DD/MM/YYYY)") },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            //todo -->datepicker y que no sea menor de 18 años
        )

        OutlinedTextField(
            value = email,
            onValueChange = { onEmailChanged(it) },
            label = { Text("Email") },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChanged(it) },
            label = { Text("Password") },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
          trailingIcon = {
                IconButton1(onClick = { passwordVisible = !passwordVisible }) {
                    Image(
                        painter = painterResource(id = if (!passwordVisible) R.drawable.visibility_off else R.drawable.visibility_on),
                        contentDescription = if (passwordVisible) "oculto" else "mostrado",
                        modifier = Modifier.size(20.dp)

                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

//        Button(
//            onClick = { /**/ },
//            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors( Color.Blue)
//        ) {
//            Text("Register", color = Color.White)
//        }
    }
}


@Composable
fun FuncionesRegistro(
    viewModel: RegistroViewModel,
    switcher: @Composable () -> Unit,
    nombre: String,
    apellidos: String,
    fechaNacimiento: String,
    email: String,
    password: String,
    registroEnable: Boolean,
    onLoginSuccess: () -> Unit
){

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LogoAppRegistro(modifier = Modifier)
        TxtsInicioRegistro()
        switcher()
        CamposRegistro(
            authViewModel = AuthViewModel(),
            nombre = nombre,
            onNombreChanged = { viewModel.onNombreChanged(it) },
            apellidos = apellidos,
            onApellidosChanged = { viewModel.onApellidosChanged(it) },
            fechaNacimiento = fechaNacimiento,
            onFechaNacimientoChanged = { viewModel.onFechaNacimientoChanged(it) },
            email = email,
            onEmailChanged = { viewModel.onEmailChanged(it) },
            password = password,
            onPasswordChanged = { viewModel.onPasswordChanged(it) },
            registroEnable = registroEnable
        )
       // ChangeViewToggleSwitch(onSelectionChanged = { /*var selected = it*/ })
        Button(
            onClick = {
                viewModel.registrarUsuaario(
                    nombre, apellidos, fechaNacimiento, email, password
                ) { success, errorMessage ->
                    if (success) {
                        onLoginSuccess() // Navegar a Home
                    } else {
                        // TODO: Mostrar mensaje de error en la UI
                        println("Error: $errorMessage")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = registroEnable,
            colors = ButtonDefaults.buttonColors(Color.Blue)
        ) {
            Text("Registrarse", color = Color.White)
        }
    }
}