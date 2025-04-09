package com.appclass.myapplication.ui.screens.mapbox

import androidx.compose.ui.graphics.vector.ImageVector

data class Filter(
    val name: String,
    val icon: ImageVector,
    val isSelected: Boolean = false
)
