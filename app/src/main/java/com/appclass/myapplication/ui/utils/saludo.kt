package com.appclass.myapplication.ui.utils

import java.util.Calendar


//obtener el saludo segun la hora
fun obtenerSaludo(): String {
    val hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hora) {
        in 6..11 -> "☀️ ¡Buenos días!"
        in 12..19 -> "🌤 ¡Buenas tardes!"
        else -> "🌙 ¡Buenas noches!"
    }
}