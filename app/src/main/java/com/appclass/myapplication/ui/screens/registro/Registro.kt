package com.appclass.myapplication.ui.screens.registro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appclass.myapplication.R
import com.appclass.myapplication.ui.screens.cambioVistasSwitch.AuthViewModel
import com.appclass.myapplication.ui.theme.Poppins
import com.appclass.myapplication.ui.theme.txtBlack

@Composable
fun Registro(viewModel: RegistroViewModel, navigateToHome: () -> Unit, switcher: @Composable () -> Unit){

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
fun ChangeViewToggleSwitch(viewModel: AuthViewModel, onSelectionChanged: (Boolean) -> Unit){

    //por el momento creamos la variable para comprobar cual es la ventana que esta seleccionada, y asi cambiar de vista
    var selected by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFFF0F0F5), shape = RoundedCornerShape(25.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { onSelectionChanged(false) },
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
            onClick = { onSelectionChanged(true) },
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
fun FuncionesRegistro(
    viewModel: RegistroViewModel,
    switcher: @Composable () -> Unit,
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
       // ChangeViewToggleSwitch(onSelectionChanged = { /*var selected = it*/ })
    }
}