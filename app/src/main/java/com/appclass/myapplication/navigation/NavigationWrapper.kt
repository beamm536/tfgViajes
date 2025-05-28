package com.appclass.myapplication.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.appclass.myapplication.data_api.api_recomendacionXcoordenadas.PlaceRecomendaciones
import com.appclass.myapplication.ui.components.barraNavegacion.NavItem
import com.appclass.myapplication.ui.components.barraNavegacion.NavigationViewModel
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.RecomendacionViewModel
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create.CrearRecomendacion1
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create.CrearRecomendacion2
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create.CrearRecomendacion3
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.create.CrearRecomendacion4
import com.appclass.myapplication.ui.screens.CRUD_recomendaciones.listar.ListarRecomendaciones
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
import com.appclass.myapplication.ui.screens.recomendacionesXgps.PlacesRecomendacionesScreen
import com.appclass.myapplication.ui.screens.recomendacionesXgps.PlacesRecomendacionesViewModel
import com.appclass.myapplication.ui.screens.recomendacionesXgps.places_details.RecomendacionesDetalles
import com.appclass.myapplication.ui.screens.recomendacionesXgps.places_details.RecomendacionesDetallesViewModel
import com.appclass.myapplication.ui.screens.registro.Registro
import com.appclass.myapplication.ui.screens.registro.RegistroViewModel
import com.appclass.myapplication.ui.screens.userProfile.Usuario
import com.appclass.myapplication.ui.screens.userProfile.UsuarioViewModel
import com.appclass.myapplication.ui.screens.userProfile.editProfile.EditarPerfil
import com.appclass.myapplication.ui.screens.userProfile.editProfile.EditarPerfilViewModel
import com.google.gson.Gson

@Composable
fun NavigationWrapper (navController: NavHostController) {

    //val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val registroViewModel: RegistroViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val editarPerfilViewModel: EditarPerfilViewModel = viewModel()
    val navigationViewModel: NavigationViewModel = viewModel()

    val placeDetailsViewModel: PlaceDetailsViewModel = viewModel()
   // val placesRecomendacionesViewModel: PlacesRecomendacionesViewModel = viewModel()

    val recomendacionesDetallesViewModel : RecomendacionesDetallesViewModel = viewModel()
    val recomendacionViewModel : RecomendacionViewModel = viewModel()

    //val mapBoxViewModel: MapBoxViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreens.PlacesRecomendacionesScreen.ruta){ //PlacesRecomendacionesScreen

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
            route = "placeDetails/{placeId}", //definimos la ruta con el parametro >>> ya que navegamos a una vista en la cual la informacion cambia segun el parametro que le proporcionemos
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

        composable (AppScreens.PlacesRecomendacionesScreen.ruta){
            PlacesRecomendacionesScreen(navController, navigationViewModel)
        }


//        composable(
//            "placeDetail/{placeJson}",
//            arguments = listOf(navArgument("placeJson") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val json = backStackEntry.arguments?.getString("placeJson")
//            val place = Gson().fromJson(json, PlaceRecomendaciones::class.java)
//            RecomendacionesDetalles(place)
//        }
//        composable("details/{placeId}") { backStackEntry ->
//            val placeId = backStackEntry.arguments?.getString("placeId") ?: ""
//            RecomendacionesDetalles(placeId = placeId, navController = navController)
//        }

        composable(
            "placeDetail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId") ?: return@composable
            RecomendacionesDetalles(placeId = placeId ?: "", navController = navController)
        }

        //VISTAS PARA LA CREACION DE LAS RECOMENDACIONES
        composable (AppScreens.CrearRecomendacion1.ruta){
            CrearRecomendacion1 (
                viewModel = recomendacionViewModel,
                onNext = {
                    navController.navigate(AppScreens.CrearRecomendacion2.ruta)
                    Log.d("NAVEGACION", "titulooo guardado, vamos con el siguiente paso")
                }
            )
        }
        composable (AppScreens.CrearRecomendacion2.ruta){
            CrearRecomendacion2 (
                viewModel = recomendacionViewModel,
                onNext = {
                    navController.navigate(AppScreens.CrearRecomendacion3.ruta)
                    Log.d("NAVEGACION", "titulooo guardado, vamos con el siguiente paso")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable (AppScreens.CrearRecomendacion3.ruta){
            CrearRecomendacion3 (
                viewModel = recomendacionViewModel,
                onNext = {
                    navController.navigate(AppScreens.CrearRecomendacion4.ruta)
                    Log.d("NAVEGACION", "titulooo guardado, vamos con el siguiente paso")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable (AppScreens.CrearRecomendacion4.ruta){
            CrearRecomendacion4 (
                navController,
                viewModel = recomendacionViewModel,
                onDone = {
                    // Puedes navegar al perfil o a un mensaje de éxito
                    navController.navigate(AppScreens.ListarRecomendaciones.ruta) {
                        popUpTo(AppScreens.CrearRecomendacion1.ruta) { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(AppScreens.ListarRecomendaciones.ruta) {
            ListarRecomendaciones(
                viewModel = recomendacionViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }



    }
}