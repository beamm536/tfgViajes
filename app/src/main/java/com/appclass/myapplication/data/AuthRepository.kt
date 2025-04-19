package com.appclass.myapplication.data

import android.util.Log
import com.google.firebase.auth.AuthCredential
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
        genero: String,
        email: String,
        password: String,
        callback: (Boolean, String?) -> Unit
    ){

        Log.d("Registro", "Intentando registrar usuario con email: $email")

        auth.createUserWithEmailAndPassword(email.trim(), password)//nuevo metodo añadido :)
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
                            "genero" to genero,
                            "email" to email
                        )

                        Log.d("Registro", "Usuario creado en Auth con UID: $uid")

                        //guardamos en la coleccion de usuarios
                        dbFirestore.collection("usuariosTFG")
                            .document(uid)
                            .set(dataUser)
                            .addOnSuccessListener {
                                Log.d("Registro", "Datos guardados en Firestore para UID: $uid")
                                callback(true, null)
                            } //registrado correctamente ^^
                            .addOnFailureListener {
                                Log.e("Registro", "Error al guardar datos en Firestore: ${it.message}")
                                callback(false, it.message)
                            }//error
                        } ?: run {
                            Log.e("Registro", "Error al registrar usuario: ${task.exception?.message}")
                            callback(false, "Usuario nulo tras registro")

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


//    fun loginConGoogle(credential: AuthCredential, home:()-> Unit){
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("Login", "Login usando Google correcto")
//                }
//            }
//            .addOnFailureListener{
//                Log.d("Login", "Error al iniciar sesión con Google")
//            }
//    }

    fun loginConGoogle(
        credential: AuthCredential,
            nombre: String,
            apellidos: String,
            email: String,
            fechaNacimiento: String,
            genero: String,
        callback: (Boolean, String?) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Login", "Login usando Google correcto")
                    val usuario = auth.currentUser
                    usuario?.let { usuarioRegistrado ->
                          val uid = usuarioRegistrado.uid
//                        val nombre = usuarioRegistrado.displayName ?: ""
//                        val apellidos = usuarioRegistrado.photoUrl?.split("/")?.last() ?: ""
//                        val email = usuarioRegistrado.email ?: ""
//                        val fechaNacimiento = usuarioRegistrado.metadata?.lastSignInDate?.toString() ?: ""
//                        val genero = usuarioRegistrado.providerData?.firstOrNull { it.providerId == "google.com" }?.uid?.split("/")?.last() ?: ""




                        //si el usuario no existe, lo creamos
                        val dataUser = hashMapOf(
                            "uid" to uid,
                            "nombre" to nombre,
                            "apellidos" to apellidos,
                            "email" to email,
                            "fechaNacimiento" to fechaNacimiento,
                            "genero" to genero
                        )
                        Log.d("nuevo usuario", "usuario creado correctamente")

                        dbFirestore.collection("usuariosTFG")
                            .document(uid)
                            .set(dataUser)
                            .addOnSuccessListener { callback(true, null) }
                            .addOnFailureListener { callback(false, it.message) }

                    } ?: run {
                        callback(false, "Usuario no encontrado después de iniciar sesión.")
                    }
                } else {
                    callback(false, task.exception?.message)
                }
            }
            .addOnFailureListener {
                callback(false, it.message)
            }
    }




    //para el logout
    fun logout(){
        auth.signOut()
    }
}