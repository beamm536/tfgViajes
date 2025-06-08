package com.appclass.myapplication.ui.screens.userProfile.editProfile

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appclass.myapplication.data.AuthRepository
import com.appclass.myapplication.models.Favorito
import com.appclass.myapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.Result.Companion.success

class EditarPerfilViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val firestore = FirebaseFirestore.getInstance()

    private val _usuario = MutableLiveData<User>()
    val usuario: LiveData<User> = _usuario

    fun cargarDatosUsuario() {
        val currentUser = authRepository.usuarioActual()
        currentUser?.let { usuarioFirebase ->
            val uid = usuarioFirebase.uid
            firestore.collection("usuariosTFG").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    document?.let {
                        val user = User(
                            uid = it.getString("uid") ?: "",
                            nombre = it.getString("nombre") ?: "",
                            apellidos = it.getString("apellidos") ?: "",
                            fechaNacimiento = it.getString("fechaNacimiento") ?: "",
                            genero = it.getString("genero") ?: "",
                            email = it.getString("email") ?: ""
                        )
                        _usuario.value = user
                    }
                }
                .addOnFailureListener { exception ->
                    // Aquí puedes loguear el error o manejarlo según necesites
                    println("Error al obtener los datos del usuario: ${exception.message}")
                }
        }
    }

    fun actualizarDatosUsuario(user: User, callback: (Boolean, String?) -> Unit) {
        val uid = user.uid
        FirebaseFirestore.getInstance().collection("usuariosTFG")
            .document(uid)
            .set(user)
            .addOnSuccessListener { callback(true, null) }
            .addOnFailureListener { callback(false, it.message) }
    }

    fun eliminarCuenta(onResult: (Boolean, String?) -> Unit){
        val user = authRepository.usuarioActual()
        val uid = user?.uid ?: return

        // 1. Eliminar de Firestore
        firestore.collection("usuariosTFG").document(uid).delete()
            .addOnSuccessListener {
                // 2. Eliminar del sistema de autenticación
                user.delete()
                    .addOnSuccessListener { onResult(true, null) }
                    .addOnFailureListener { onResult(false, it.message) }
                FirebaseAuth.getInstance().signOut()
            }
            .addOnFailureListener {
                onResult(false, it.message)
            }
    }
}