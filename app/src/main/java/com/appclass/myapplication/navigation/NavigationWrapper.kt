package com.appclass.myapplication.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.appclass.myapplication.ui.components.barraNavegacion.NavItem
import com.appclass.myapplication.ui.screens.prueba.MapOnlyScreen
import com.appclass.myapplication.ui.screens.cambioVistasSwitch.Auth
import com.appclass.myapplication.ui.screens.cambioVistasSwitch.AuthViewModel
import com.appclass.myapplication.ui.screens.cambioVistasSwitch.LoginSignUpSwitcher
import com.appclass.myapplication.ui.screens.googleplacesdetails.PlaceDetails
import com.appclass.myapplication.ui.screens.googleplacesdetails.PlaceDetailsViewModel
import com.appclass.myapplication.ui.screens.googleplacesdetails.Places
import com.appclass.myapplication.ui.screens.home.PruebaExplorarLugaresScreen
import com.appclass.myapplication.ui.screens.login.Login
import com.appclass.myapplication.ui.screens.login.LoginViewModel
import com.appclass.myapplication.ui.screens.mapbox.MapBox
import com.appclass.myapplication.ui.screens.mapbox.MapBoxScreen
import com.appclass.myapplication.ui.screens.mapbox.detalleMapa.DetalleMapaScreen
import com.appclass.myapplication.ui.screens.registro.Registro
import com.appclass.myapplication.ui.screens.registro.RegistroViewModel
import com.appclass.myapplication.ui.screens.userProfile.Usuario
import com.appclass.myapplication.ui.screens.userProfile.UsuarioViewModel
import com.appclass.myapplication.ui.screens.userProfile.editProfile.EditarPerfil
import com.appclass.myapplication.ui.screens.userProfile.editProfile.EditarPerfilViewModel

@Composable
fun NavigationWrapper (navController: NavHostController) {

    //val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val registroViewModel: RegistroViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val editarPerfilViewModel: EditarPerfilViewModel = viewModel()

    val placeDetailsViewModel: PlaceDetailsViewModel = viewModel()

    //val mapBoxViewModel: MapBoxViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreens.Places.ruta){

        composable(AppScreens.Auth.ruta){
            Auth(
                navController = navController,
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
                navController = navController,
                viewModel = loginViewModel,
                switcher = { LoginSignUpSwitcher(authViewModel) },
            )
        }

        composable(AppScreens.Registro.ruta){
            Registro(
                viewModel = registroViewModel,
                switcher = { LoginSignUpSwitcher(authViewModel) },
                navigateToHome = {
                    Log.d("Navigation", "🎯 ¡NavigateToHome ejecutado!")
                    navController.navigate(NavItem.Profile.route){
                        popUpTo(AppScreens.Registro.ruta) { inclusive = true }
                    }
                    //todo: si no funciona probar con esto --> navController.navigate("profile")
                }
               // navigateToHome = { navController.navigate(AppScreens.EditarPerfil.ruta) }
            )
        }

//        composable(AppScreens.Usuario.ruta){
//            Usuario(
//                viewModel = usuarioViewModel,
//                navController = navController
//            )
//        }

        composable(NavItem.Profile.route) {
            Usuario(
                viewModel = usuarioViewModel,
                navController = navController
            )
        }

        composable(AppScreens.EditarPerfil.ruta){
            EditarPerfil(
                viewModel = editarPerfilViewModel,
                navController = navController
            )
        }


//        composable(AppScreens.MapBox.ruta){
//            MapBox(
//                viewModel = mapBoxViewModel,
//                navController = navController
//            )
//        }
        composable(AppScreens.MapBox.ruta) {
            MapBoxScreen(navController)
        }
        composable(NavItem.Search.route) {
            MapBoxScreen(
                navController = navController
            )
        }


        composable(AppScreens.MapOnly.ruta) {
            MapOnlyScreen(navController)
        }


        composable(
            route = AppScreens.DetalleMapa.ruta,
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("lat") { type = NavType.StringType },
                navArgument("lon") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val lat = backStackEntry.arguments?.getString("lat")?.toDoubleOrNull() ?: 0.0
            val lon = backStackEntry.arguments?.getString("lon")?.toDoubleOrNull() ?: 0.0

            DetalleMapaScreen(nombre, lat, lon, viewModel())
        }



        composable(AppScreens.PruebaExplorarLugares.ruta) {
            PruebaExplorarLugaresScreen(navController)
        }

        composable(
           // AppScreens.PlaceDetailsGoogle.ruta
            route = "placeDetails/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ){ backStackEntry ->
            PlaceDetails(
                navController,
                viewModel(),
                navBackStackEntry = backStackEntry
            )
        }

        composable(AppScreens.Places.ruta){
            Places(navController)
        }
    }
}