package com.appclass.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appclass.myapplication.ui.screens.login.Login
import com.appclass.myapplication.ui.screens.login.LoginViewModel
import com.appclass.myapplication.ui.screens.registro.Registro
import com.appclass.myapplication.ui.screens.registro.RegistroViewModel

@Composable
fun NavigationWrapper (navController: NavHostController) {

    //val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    val registroViewModel: RegistroViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreens.Login.ruta){

        composable(AppScreens.Login.ruta){
            /**
            viewModel --> para la conexion de la vista con la logica
             */
            Login(
                viewModel = loginViewModel,
                navigateToHome = { navController.navigate(AppScreens.Registro.ruta) }
            )
        }

        composable(AppScreens.Registro.ruta){
            Registro(
                viewModel = registroViewModel,
                navigateToHome = {/*todo --> navegacion entre pantallas sin qe me de error :)*/}
            )
        }
    }
}