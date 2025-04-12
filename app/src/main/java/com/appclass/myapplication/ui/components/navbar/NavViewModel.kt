package com.appclass.myapplication.ui.components.navbar

import androidx.lifecycle.ViewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
class NavViewModel : ViewModel(){
    val navItems = listOf(
        //routes ----- icon -----label
        NavItem("FEHome", Icons.Default.Send, "Sent"),
        NavItem("home", Icons.Default.Home, "Home"),
        NavItem("FRHome", Icons.Default.Star, "Received")
    )
}