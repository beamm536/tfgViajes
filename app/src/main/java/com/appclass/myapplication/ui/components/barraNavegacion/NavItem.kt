package com.appclass.myapplication.ui.components.barraNavegacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.appclass.myapplication.navigation.AppScreens

/**
 * SEALED CLASS --> para modelar los estados finitos / posibles estados, que nos podemos encontrar como resultado de una operación
 * - F : representacion de un conjunto limitado de objetos / o de estados / eventos que pueda manejar la aplicación
 *
 * ------------------
 * Cada NavItem es único, y cada uno de ellos cuenta con 3 propiedades : la ruta / el icono / label(nombre, definición)
 * Definimos los items/objetos que vamos a tener en nuestra barra de navegación
 *
 */

sealed class NavItem (
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    //object Home : NavItem("home", Icons.Default.Home, "Inicio")
    object Home : NavItem(AppScreens.PlacesRecomendacionesScreen.ruta, Icons.Default.Home, "Inicio")
    //object Home : NavItem("PlacesRecomendacionesScreen", Icons.Default.Home, "Inicio")
    object Search : NavItem("mapbox", Icons.Default.Search, "Buscar")
    object Map : NavItem("CrearRecomendacion1", Icons.Default.Add, "Mapa")
    //object Chat : NavItem("chat", Icons.Default.List, "Chat")
    object Chat : NavItem("ListarRecomendaciones", Icons.Default.List, "Chat")
    object Profile : NavItem("usuario", Icons.Default.Person, "Perfil")
}
