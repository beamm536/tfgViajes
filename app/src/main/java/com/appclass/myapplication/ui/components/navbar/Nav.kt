package com.appclass.myapplication.ui.components.navbar


import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

import androidx.compose.material3.*
import androidx.compose.runtime.getValue


@Composable
fun BottomNavigationBarComponent(
    navController: NavController,
    viewModel: NavViewModel = viewModel()
) {
    // Obtener la ruta de la pantalla seleccionada en el stack de navegación
    val selectedRoute by navController.currentBackStackEntryAsState()

    // Barra de navegación inferior
    NavigationBar(containerColor = Color(0xFF9BB7C5)) {
        // Iterar sobre los elementos de navegación definidos en el ViewModel
        viewModel.navItems.forEach { item ->
            // Verificar si el item es el seleccionado
            val isSelected = item.route == selectedRoute?.destination?.route

            // Crear el ítem de navegación
            NavigationBarItem(
                icon = {
                    // Ícono del item con tamaño ajustable según selección
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(if (isSelected) 30.dp else 24.dp)
                    )
                },
                label = {
                    // Etiqueta del item con estilo ajustable según selección
                    Text(
                        text = item.label,
                        fontSize = if (isSelected) 12.sp else 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selected = isSelected,
                onClick = {
                    // Si la ruta seleccionada es 'Perfil', no hacer nada si ya está seleccionado
                    if (item.route == "Perfil" && selectedRoute?.destination?.route == "Perfil") return@NavigationBarItem

                    // Navegar a 'Home' si no está en 'Home' o navegar al destino seleccionado
                    if (item.route == "Home" || item.route == "Perfil") {
                        navController.navigate("home") {
                            // Evitar la actualización constante de la pantalla de inicio
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        navController.navigate(item.route) {
                            // Guardar el estado y no duplicar
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}