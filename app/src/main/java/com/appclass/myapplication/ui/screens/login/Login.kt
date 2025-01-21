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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.appclass.myapplication.R
import com.appclass.myapplication.ui.theme.Poppins
import com.appclass.myapplication.ui.theme.txtBlack

@Composable
fun Login(viewModel: LoginViewModel, navigateToHome: () -> Unit){
//    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        Spacer(modifier = Modifier.weight(1f))
//        Text(text = "LOGIN SCREEN", fontSize = 25.sp)
//
//        //BlurredCardWithImage()
//
//        //Spacer(modifier = Modifier.weight(1f))
//
//
//        Button(onClick = { navigateToHome() }) {
//            Text(text = "Navegar a la home")
//        }
//        Spacer(modifier = Modifier.weight(1f))
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
                onLoginSuccess = navigateToHome
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
fun LoginBodyScreen(/*viewModel: LoginViewModel, email:String, onTextFieldChanged:(String)*/){
    //var email by remember { mutableStateOf("") }
    //var password by remember { mutableStateOf("") }

    //con esto enganchamos la variable instanciada en el ViewModel con la ui
    //val email: String by viewModel.email.observeAsState(initial = "")

    var passwordVisible by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
//        Row(modifier = Modifier.fillMaxWidth()) {
//            Text("Log In", fontSize = 18.sp, modifier = Modifier.weight(1f))
//            Text("Sign Up", fontSize = 18.sp, color = Color.Gray)
//        }

        //llamada al switch de LOGIN y SIGNUP
        var selected by remember { mutableStateOf(true) }
        LoginSignUpSwitcher(selected = selected, onSelectionChanged = { selected = it })

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",//email,
            onValueChange = { /*onTextFieldChanged(it)*/ },
            label = { Text("Email") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {  },
            label = { Text("Password") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            trailingIcon = {
//                //val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                    Icon(imageVector = image, contentDescription = null)
//                }
//            }
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
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Blue)
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
fun LoginSignUpSwitcher(selected: Boolean, onSelectionChanged: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFFF0F0F5), shape = RoundedCornerShape(25.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { onSelectionChanged(true) },
            colors = ButtonDefaults.buttonColors(
                if (selected) Color.White else Color.Transparent,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .weight(1f)
                .height(42.dp),
            shape = RoundedCornerShape(25.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text("Log In")
        }

        Button(
            onClick = { onSelectionChanged(false) },
            colors = ButtonDefaults.buttonColors(
                if (!selected) Color.White else Color.Transparent,
                contentColor = txtBlack
            ),
            modifier = Modifier
                .weight(1f)
                .height(42.dp),
            shape = RoundedCornerShape(25.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text("Sign Up")
        }
    }
}

@Composable
fun FuncionesLogin(viewModel: LoginViewModel, onLoginSuccess: () -> Unit){
    //onLoginSuccess --> esta en LoginViewModel

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LogoAppLogin(modifier = Modifier)
        TxtsInicio()
        //para la recarga y comprobacion constante de los datos, desde que el programa nota q el usuario esta realizando cambios
        LoginBodyScreen(/*email, {viewModel.onLoginChange(it)}*/)
    }
}
