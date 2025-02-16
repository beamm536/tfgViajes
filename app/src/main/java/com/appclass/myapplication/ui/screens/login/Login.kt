package com.appclass.myapplication.ui.screens.login

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.appclass.myapplication.R
import com.appclass.myapplication.ui.screens.cambioVistasSwitch.AuthViewModel
import com.appclass.myapplication.ui.theme.Poppins
import com.appclass.myapplication.ui.theme.txtBlack

@Composable
fun Login(navController: NavController, viewModel: LoginViewModel, switcher: @Composable () -> Unit){

    //DECLARACION DE LAS VARIABLES DE ESETADO DEL VIEWMODEL
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false) //nuestro boton empieza deshabilitado
    //val navigateToUser by viewModel.navigateToUser.observeAsState(false)

    // Si el usuario ha iniciado sesión, navegamos a la pantalla de usuario
//    if (navigateToUser) {
//        navController.navigate("Usuario") {
//            popUpTo("Login") { inclusive = true } // Evita que vuelva al login con "atrás"
//        }
//        viewModel.resetNavigation() // Reseteamos el estado después de navegar
//    }


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
            FuncionesLogin(
                viewModel = viewModel,
                switcher = switcher,
                email = email,
                password = password,
                loginEnable = loginEnable,
                onLoginSuccess = { navController.navigate("Usuario") }
            )
        }
    }
}

//TODO --> pendiente de incorporacion un pattern de fondo como tengo en el prototipo

@Composable
fun LogoAppLogin(modifier: Modifier){
    Image(
        painter = painterResource(id = R.drawable.primerlogo_app_removebg_preview),
        contentDescription = "logoApp_pantallaLogin",
        modifier = Modifier.size(75.dp, 75.dp)
    )
}
@Composable
fun TxtsInicio(){
    Text(
        text = "Welcome Back!",
        fontSize = 32.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        color = txtBlack
    )
}

@Composable
fun LoginBodyScreen(
    authViewModel: AuthViewModel,
    email: String,
    onEmailChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    loginEnable: Boolean,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel
){

    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange ={ onEmailChanged(it) }, //es lo mismo que onTextFieldChanged(email, it) -> pero para q no de error con los parámetros
            label = { Text("Email") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChanged(it) },
            label = { Text("Password") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Image(
                        painter = painterResource(id = if (!passwordVisible) R.drawable.visibility_off else R.drawable.visibility_on),
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                        modifier = Modifier.size(20.dp)

                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(8.dp))

        ClickableText(
            text = AnnotatedString("Forgot Password ?"),
            onClick = {},
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            //onClick = { onLoginSelected() },
            onClick = { viewModel.onLoginSelected(onLoginSuccess) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Blue),
            enabled = loginEnable
        ) {
            Text("Log In", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Or login with",
                //modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.White)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp) //spacedBy --> espacio entre el icono y el txt del btn
            ){
                Image(
                    painter = painterResource(R.drawable.google_logintfg),
                    contentDescription = "icono google - metodo autenticacion",
                    modifier = Modifier
                        .size(20.dp)
                )
                Text("Continue with Google", color = txtBlack)
            }

        }
    }
}


@Composable
fun FuncionesLogin(
    viewModel: LoginViewModel,
    switcher: @Composable () -> Unit,
    email: String,
    password: String,
    onLoginSuccess: () -> Unit,
    loginEnable: Boolean
){

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LogoAppLogin(modifier = Modifier)
        TxtsInicio()
        /** SWITCH --> CON VARIABLES COMPARTIDAS - AUTHVIEWMODEL */
        switcher()
        LoginBodyScreen(
            authViewModel = AuthViewModel(),
            email = email,
            onEmailChanged = { viewModel.onEmailChanged(it) },
            password = password,
            onPasswordChanged = { viewModel.onPasswordChanged(it) },
            loginEnable = loginEnable,
            onLoginSuccess = onLoginSuccess,
            //onLoginSelected = { viewModel.onLoginSelected(onLoginSuccess) },
            viewModel = viewModel
        )
    }
}
