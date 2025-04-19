package com.appclass.myapplication.ui.screens.userProfile.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appclass.myapplication.data.AuthRepository
import com.appclass.myapplication.models.User
import com.google.firebase.firestore.FirebaseFirestore

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
}