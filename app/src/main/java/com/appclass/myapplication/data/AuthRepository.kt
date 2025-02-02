package com.appclass.myapplication.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

/**
 * en esta clase --> vamos a realizar la conexion a la base de datos,
 * de momento para el login y registro
 */
class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    //comprobamos si el usuairo ya esta autenticado | si hya alguien logeado nos devolvera el objeto currentUser
    fun usuarioActual(): FirebaseUser? {
        return auth.currentUser
    }

    //crear nuevo usuario
    fun registrarUsuario(
        nombre: String,
        apellidos: String,
        fechaNacimiento: String,
        email: String,
        password: String,
        callback: (Boolean, String?) -> Unit
    ){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val usuario =auth.currentUser
                    usuario?.let { usuarioRegistrado ->
                        val uid = usuarioRegistrado.uid
                        val dataUser = hashMapOf(
                            "uid" to uid,
                            "nombre" to nombre,
                            "apellidos" to apellidos,
                            "fechaNacimiento" to fechaNacimiento,
                            "email" to email
                        )
                        //guardamos en la coleccion de usuarios
                        dbFirestore.collection("usuariosTFG")
                            .document(uid)
                            .set(dataUser)
                            .addOnSuccessListener { callback(true, null) } //registrado correctamente ^^
                            .addOnFailureListener { callback(false, it.message) }//error
                    }

                } else {
                    callback(false, task.exception?.message) //error
                }
            }
    }

    //iniciar sesion con un usuario ya existente
    fun iniciarSesion(email: String, password: String, callback: (Boolean, String?) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null) //iniciado correctamente ^^
                } else {
                    callback(false, task.exception?.message) //error
                }
            }

    }


    //para el logout
    fun logout(){
        auth.signOut()
    }
}