package com.appclass.myapplication.ui.components.barraNavegacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem (
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : NavItem("home", Icons.Default.Home, "Inicio")
    object Search : NavItem("search", Icons.Default.Search, "Buscar")
    object Map : NavItem("map", Icons.Default.Place, "Mapa")
    object Chat : NavItem("chat", Icons.Default.List, "Chat")
    object Profile : NavItem("profile", Icons.Default.Person, "Perfil")
}
