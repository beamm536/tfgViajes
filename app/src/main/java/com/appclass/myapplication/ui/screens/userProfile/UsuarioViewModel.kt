package com.appclass.myapplication.ui.screens.userProfile

import androidx.lifecycle.ViewModel
import com.appclass.myapplication.models.Favorito
import com.appclass.myapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsuarioViewModel: ViewModel(){

    private val _nombreUsuario = MutableStateFlow("")
    val nombreUsuario: StateFlow<String> = _nombreUsuario

    private val _usuario = MutableStateFlow<User?>(null)
    val usuario: StateFlow<User?> = _usuario

    private val _favoritos = MutableStateFlow<List<Favorito>>(emptyList())
    val favoritos: StateFlow<List<Favorito>> = _favoritos

    init {
        cargarFavoritos()
        cargarNombreUsuario()
    }

    fun cargarNombreUsuario()
    {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("usuariosTFG") // asegúrate de que sea el nombre correcto de tu colección
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val nombre = doc.getString("nombre") ?: "Usuario"
                    _nombreUsuario.value = nombre
                }
            }
    }
    fun cargarFavoritos() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseFirestore.getInstance().collection("favoritos")
            .whereEqualTo("userId", uid)
//            .get()
//            .addOnSuccessListener { docs ->
//                _favoritos.value = docs.mapNotNull { it.toObject(Favorito::class.java) }
//            }
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    _favoritos.value = snapshot.mapNotNull { it.toObject(Favorito::class.java) }
                }
            }
    }

    fun eliminarFavorito(favorito: Favorito) {
        FirebaseFirestore.getInstance()
            .collection("favoritos")
            .whereEqualTo("userId", favorito.userId)
            .whereEqualTo("lugarId", favorito.lugarId)
            .get()
            .addOnSuccessListener { docs ->
                for (doc in docs) {
                    FirebaseFirestore.getInstance().collection("favoritos").document(doc.id).delete()
                }

                //actualizamos lista local de favoritos que le quedan al usuario
                _favoritos.value = _favoritos.value.filterNot { it.lugarId == favorito.lugarId }
            }
    }

}