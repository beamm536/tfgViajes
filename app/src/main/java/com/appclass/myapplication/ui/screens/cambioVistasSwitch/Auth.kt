package com.appclass.myapplication.ui.screens.cambioVistasSwitch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appclass.myapplication.ui.screens.login.Login
import com.appclass.myapplication.ui.screens.login.LoginViewModel
import com.appclass.myapplication.ui.screens.registro.Registro
import com.appclass.myapplication.ui.screens.registro.RegistroViewModel
import com.appclass.myapplication.ui.theme.txtBlack

/**
 * Esta clase es para que se pueda compartir la variable del switch, y se mantenga
 * el estado en el que esta se encuentra.
 * Que la vista cambie en funcion del valor de la variable que detecte
 */
@Composable
fun Auth(viewModel: AuthViewModel, loginViewModel: LoginViewModel, navigateToHome: () -> Unit, registroViewModel: RegistroViewModel) {
    val isLoginSelected: Boolean by viewModel.isLoginSelected.observeAsState(initial = true)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //LoginSignUpSwitcher(viewModel) // El switcher arriba del todo en las vistas

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoginSelected) {
            Login(viewModel = loginViewModel, navigateToHome = navigateToHome, switcher = { LoginSignUpSwitcher(viewModel) })
        } else {
            Registro(viewModel = registroViewModel, navigateToHome, switcher = { LoginSignUpSwitcher(viewModel) })
        }
    }
}

@Composable
fun LoginSignUpSwitcher(authViewModel: AuthViewModel) { //con esto recibe toggleSelection

    //LOGIN --> true || REGISTRO --> false
    val isLoginSelected: Boolean by authViewModel.isLoginSelected.observeAsState(initial = true)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFFF0F0F5), shape = RoundedCornerShape(25.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        //CUANDO ESTAMOS EN LOGIN, NOS REDIRIGE AL REGISTRO --> ESTANDO EN TRUE
        Button(
            onClick = { authViewModel.toggleSelection(true) },
            colors = ButtonDefaults.buttonColors(
                if (isLoginSelected) Color.White else Color.Transparent,
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

        //CUANDO ESTAMOS EN REGISTRO, NOS REDIRIGE AL LOGIN --> ESTANDO EN FALSE
        Button(
            onClick = { authViewModel.toggleSelection(false) },
            colors = ButtonDefaults.buttonColors(
                if (!isLoginSelected) Color.White else Color.Transparent,
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

