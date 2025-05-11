package com.appclass.myapplication.ui

fun main(){
    //valor fijo - inmutable
    val nombre: String = "ana"

    //nombre = "pepe" // VAL CAN NOT BE REASIGNED

    println("nombre $nombre")



    //variable - despues de instanciarlo si podemos cambiar el valor
    var edad: Int = 25
    println("edad $edad") //edad == 25

    edad = 26
    println("edad actualizada $edad") //edad == 26

}