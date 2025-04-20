package com.appclass.myapplication.ui.screens.registro

import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import java.util.Calendar
import androidx.compose.material3.IconButton as IconButton1

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Registro(viewModel: RegistroViewModel, navigateToHome: () -> Unit, switcher: @Composable () -> Unit){

    //DECLARACION DE LAS VARIABLES DE ESETADO DEL VIEWMODEL
    val nombre: String by viewModel.nombre.observeAsState(initial = "")
    val apellidos: String by viewModel.apellidos.observeAsState(initial = "")
    val fechaNacimiento: String by viewModel.fechaNacimiento.observeAsState(initial = "")
    val genero: String by viewModel.genero.observeAsState(initial = "")
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val registroEnable: Boolean by viewModel.registroEnable.observeAsState(initial = false) //nuestro boton empieza deshabilitado

    var passwordVisible by remember { mutableStateOf(false) }

    /**
     * funcion creada pq everything me ha dado error, a lo qe respecta de la navegación después del registro de un nuevo usaurio dentro de la app
     */
    val registroExitoso by viewModel.registroExitoso.observeAsState()
    LaunchedEffect(registroExitoso) {
        if (registroExitoso == true) {
            Log.d("Registro", "🔥 LiveData detectó éxito. Navegando.")
            navigateToHome()
        }
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

            Text(text = "Iniciar sesión")

            //las llamadas al resto de funciones las haremos en FuncionesLogin
            FuncionesRegistro(
                viewModel = viewModel,
                switcher = switcher,
                nombre = nombre,
                apellidos = apellidos,
                fechaNacimiento = fechaNacimiento,
                genero = genero,
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
    viewModel: RegistroViewModel,
    authViewModel: AuthViewModel,
    nombre: String,
    onNombreChanged: (String) -> Unit,
    apellidos: String,
    onApellidosChanged: (String) -> Unit,
    fechaNacimiento: String,
    onFechaNacimientoChanged: (String) -> Unit,
    genero: String,
    onGeneroChanged: (String) -> Unit,
    email: String,
    onEmailChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    registroEnable: Boolean
){

    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

   // val viewModel: RegistroViewModel = viewModel() //para la llamada a la funcion para la validacion de edad en el registro

    //var fechaNacimiento by remember { mutableStateOf("") }

//    val datePicker = remember {
//        DatePickerDialog(
//            context,
//            { _, year, month, dayOfMonth ->
//                val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
//                if (viewModel.validacionMayorEdad(selectedDate)) {
//                    onFechaNacimientoChanged(selectedDate)
//                } else {
//                    Toast.makeText(context, "Debes tener al menos 18 años", Toast.LENGTH_SHORT).show()
//                }
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        )
//    }

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

        CalendarioRegistro(
            fechaNacimiento = fechaNacimiento,
            onFechaNacimientoChanged = onFechaNacimientoChanged,
            viewModel = viewModel
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

    }
}


@Composable
fun FuncionesRegistro(
    viewModel: RegistroViewModel,
    switcher: @Composable () -> Unit,
    nombre: String,
    apellidos: String,
    fechaNacimiento: String,
    genero: String,
    email: String,
    password: String,
    registroEnable: Boolean,
    onLoginSuccess: () -> Unit
){
    val context = LocalContext.current


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
            viewModel = viewModel,
            authViewModel = AuthViewModel(),
            nombre = nombre,
            onNombreChanged = { viewModel.onNombreChanged(it) },
            apellidos = apellidos,
            onApellidosChanged = { viewModel.onApellidosChanged(it) },
            fechaNacimiento = fechaNacimiento,
            onFechaNacimientoChanged = { viewModel.onFechaNacimientoChanged(it) },
            genero = genero,
            onGeneroChanged = { viewModel.onGeneroChanged(it) },
            email = email, //ANTES - email ---- cambiado para pruebas "user${System.currentTimeMillis()}@gmail.com"
            onEmailChanged = { viewModel.onEmailChanged(it) },
            password = password,
            onPasswordChanged = { viewModel.onPasswordChanged(it) },
            registroEnable = registroEnable
        )
       // ChangeViewToggleSwitch(onSelectionChanged = { /*var selected = it*/ })
        Button(
            onClick = {
                viewModel.registrarUsuaario(
                    nombre, apellidos, fechaNacimiento, genero,email, password
                ) { success, errorMessage ->
                    Log.d("Registro", "callback result: success=$success, error=$errorMessage")

                    if (success) {
                        Log.d("Registro", "Registro exitoso. Llamando a onLoginSuccess()")
                        onLoginSuccess()
                    } else {
                        Log.e("Registro", "Error en registro: $errorMessage")

                        Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
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


@Composable
fun CalendarioRegistro(
    fechaNacimiento: String,
    onFechaNacimientoChanged: (String) -> Unit,
    viewModel: RegistroViewModel
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    if (fechaNacimiento.isNotEmpty()) {
        try {
            val partes = fechaNacimiento.split("/")
            val day = partes[0].toInt()
            val month = partes[1].toInt() - 1
            val year = partes[2].toInt()
            calendar.set(year, month, day)
        } catch (e: Exception) { }
    }

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val showDatePicker = remember { mutableStateOf(false) }

    if (showDatePicker.value) {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val fecha = "%02d/%02d/%04d".format(selectedDay, selectedMonth + 1, selectedYear)
                if (viewModel.validacionMayorEdad(fecha)) {
                    onFechaNacimientoChanged(fecha)
                } else {
                    Toast.makeText(context, "Debes tener al menos 18 años", Toast.LENGTH_SHORT).show()
                }
                showDatePicker.value = false
            },
            year, month, day
        ).show()
    }

    OutlinedTextField(
        value = fechaNacimiento,
        onValueChange = { }, // El usuario no puede escribir directamente
        label = { Text("Fecha de nacimiento") },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDatePicker.value = true },
        trailingIcon = {
            IconButton(onClick = { showDatePicker.value = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Seleccionar fecha"
                )
            }
        }
    )
}

