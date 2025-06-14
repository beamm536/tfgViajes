package com.appclass.myapplication.ui.components.barraNavegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * UI de nuestro componente bottombar
 */

@Composable
fun BottomNavBar(navController: NavController, viewModel: NavigationViewModel) {
    //val selectedItem by viewModel.iconoSeleccionado

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    //val currentroute = navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry?.destination?.route)

    val items = listOf(
        NavItem.Home,
        NavItem.Search,
        NavItem.Map,
        NavItem.Chat,
        NavItem.Profile
    )

    NavigationBar(
        containerColor = Color(0xFF2596BE),
        tonalElevation = 6.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                selected = item.route == currentRoute/*selectedItem*/,
                onClick = {
                    viewModel.onItemSelected(item)
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.White,
                    indicatorColor = Color.Transparent
                ),
                alwaysShowLabel = false //false
            )
        }
    }
}