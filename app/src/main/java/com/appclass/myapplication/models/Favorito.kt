package com.appclass.myapplication.models

import com.google.firebase.Timestamp

data class Favorito(
    val favoritoId: String = "", //lo usamos solamente localmente
    val userId: String = "",
    val lugarId: String = "",
    val ubicacion: String = "",
    val descripcion: String = "",
    val fechaGuardado: Timestamp = Timestamp.now(),
    val nombreLugar: String = ""
)
