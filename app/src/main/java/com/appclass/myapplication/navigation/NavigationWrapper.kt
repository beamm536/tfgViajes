package com.appclass.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appclass.myapplication.ui.screens.cambioVistasSwitch.Auth
import com.appclass.myapplication.ui.screens.cambioVistasSwitch.AuthViewModel
import com.appclass.myapplication.ui.screens.cambioVistasSwitch.LoginSignUpSwitcher
import com.appclass.myapplication.ui.screens.login.Login
import com.appclass.myapplication.ui.screens.login.LoginViewModel
import com.appclass.myapplication.ui.screens.registro.Registro
import com.appclass.myapplication.ui.screens.registro.RegistroViewModel

@Composable
fun NavigationWrapper (navController: NavHostController) {

    //val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val registroViewModel: RegistroViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreens.Auth.ruta){

        composable(AppScreens.Auth.ruta){
            Auth(
                viewModel = authViewModel as AuthViewModel, //para cambiarle el nombre :) pq me daba error jejej
                loginViewModel = loginViewModel,
                registroViewModel = registroViewModel,
                navigateToHome = { navController.navigate(AppScreens.Registro.ruta) }
            )
        }

        composable(AppScreens.Login.ruta){
            /**
            viewModel --> para la conexion de la vista con la logica
             */
            Login(
                viewModel = loginViewModel,
                switcher = { LoginSignUpSwitcher(authViewModel) },
                navigateToHome = { navController.navigate(AppScreens.Registro.ruta) }
            )
        }

        composable(AppScreens.Registro.ruta){
            Registro(
                viewModel = registroViewModel,
                switcher = { LoginSignUpSwitcher(authViewModel) },
                navigateToHome = { navController.navigate(AppScreens.Login.ruta) }
            )
        }
    }
}