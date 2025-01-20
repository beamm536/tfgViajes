package com.appclass.myapplication.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
//            FuncionesLogin(
//                viewModel = viewModel,
//                onLoginSuccess = navigateToHome
//            )
        }
    }
}