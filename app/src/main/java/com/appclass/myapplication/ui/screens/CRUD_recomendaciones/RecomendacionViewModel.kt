package com.appclass.myapplication.ui.screens.CRUD_recomendaciones

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.appclass.myapplication.data.recomendaciones.UserRecomendation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.hamcrest.Description
import java.util.UUID

class RecomendacionViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    //TITULO DE LA RECOMENDACION
    private val _title = mutableStateOf("")
    val title: State<String> = _title

    fun onTitleChanged(newTitle: String) {
        _title.value = newTitle
    }

    //DESCRIPCION
    private val _description = mutableStateOf("")
    val description: State<String> = _description

    fun onDescriptionChanged(newDescription: String){
        _description.value = newDescription
    }

    //LOCALIZACION
    private val _locationName = mutableStateOf("")
    val locationName: State<String> = _locationName

    private val _latLng = mutableStateOf<Pair<Double, Double>?>(null)
    val latLng: State<Pair<Double, Double>?> = _latLng

    fun onLocationChanged(name: String) {
        _locationName.value = name
    }

    fun geocodeLocation(context: Context, onSuccess: () -> Unit, onError: (String) -> Unit) {
        try {
            val geocoder = Geocoder(context)
            val results = geocoder.getFromLocationName(_locationName.value, 1)
            if (!results.isNullOrEmpty()) {
                val location = results[0]
                _latLng.value = location.latitude to location.longitude
                onSuccess()
            } else {
                onError("No se encontró esa ubicación")
            }
        } catch (e: Exception) {
            onError("Error al buscar ubicación: ${e.message}")
        }
    }

    //CATEGORIA
    private val _category = mutableStateOf("")
    val category: State<String> = _category

    fun onCategorySelected(newCategory: String) {
        _category.value = newCategory
    }

    //ULTIMOS CAMPOS -seguridad y demas
    private val _isPublic = mutableStateOf(true)
    val isPublic: State<Boolean> = _isPublic

    fun onVisibilityChanged(public: Boolean) {
        _isPublic.value = public
    }

    private val _userRecommendations = mutableStateListOf<UserRecomendation>()
    val userRecommendations: List<UserRecomendation> = _userRecommendations


//    fun createRecommendation(onSuccess: () -> Unit, onError: (Exception) -> Unit) {
//        //val userId = auth.currentUser?.uid ?: return CON ESTA LINEA HACEMOS QE SEA NECESARIO INCIAR SESION EN LA APP
//        val userId = auth.currentUser?.uid ?: "user_dev_test" //para hacer pruebas  :) 1DOYtcuQjlhdcn7sWyFR3xaugfA2
//
//        if(userId == null){
//            Log.d("Firebase","usuario no logueado")
//        }
//
//        val id = UUID.randomUUID().toString()
//        val latLng = _latLng.value
//
//        if (latLng == null) {
//            onError(Exception("Ubicación no válida"))
//            return
//        }

//        val recommendation = UserRecomendation( //nuestro data class - modelo de datos
//            id = UUID.randomUUID().toString(),
//            title = _title.value,
//            description = _description.value,
//            locationName = _locationName.value,
//            lat = latLng.first,
//            lng = latLng.second,
//            category = _category.value,
//            isPublic = _isPublic.value,
//            userId = userId,
//            createdAt = System.currentTimeMillis()
//        )
//
//        Log.d("Firebase", "Enviando datos a firestore: $recommendation")
//
//        db.collection("user_recomendaciones")
//            .document(id)
//            .set(recommendation)
//            .addOnSuccessListener {
//                Log.d("Firebase", "✅ Recomendación guardada")
//                onSuccess()
//            }
//            .addOnFailureListener { e ->
//                Log.e("Firebase", "❌ Error al guardar: ${e.message}")
//                onError(e)
//            }


//        // 🔍 Prueba con datos forzados (esto debería funcionar siempre)
//        val recommendation = UserRecomendation(
//            id = UUID.randomUUID().toString(),
//            title = "Test título",
//            description = "Test descripción",
//            locationName = "Test ubicación",
//            lat = 40.0,
//            lng = -3.0,
//            category = "Test categoría",
//            isPublic = true,
//            userId = userId,
//            createdAt = System.currentTimeMillis()
//        )
//
//        Log.d("FirestoreDebug", "📝 Intentando guardar: $recommendation")
//
//        db.collection("user_recomendaciones")
//            .document(recommendation.id)
//            .set(recommendation)
//            .addOnSuccessListener {
//                Log.d("FirestoreDebug", "✅ Guardado exitoso")
//                onSuccess()
//            }
//            .addOnFailureListener { e ->
//                Log.e("FirestoreDebug", "❌ Error al guardar: ${e.message}")
//                onError(Exception("Firestore error: ${e.message}"))
//            }
//    }

//    fun createRecommendation(onSuccess: () -> Unit, onError: (Exception) -> Unit) {
//        val testData = mapOf(
//            "title" to "Test place",
//            "description" to "Descripción de prueba",
//            "userId" to "debug-user",
//            "createdAt" to System.currentTimeMillis()
//        )
//
//        db.collection("user_recomendaciones")
//            .add(testData)
//            .addOnSuccessListener {
//                Log.d("Firebase", "✅ Documento de prueba creado")
//                onSuccess()
//            }
//            .addOnFailureListener {
//                Log.e("Firebase", "❌ Error al guardar: ${it.message}")
//                onError(it)
//            }
//    }



    fun createRecommendation(onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid ?: "debug-user"
        val latLng = _latLng.value

        // ✅ Validación antes de guardar
        if (_title.value.isBlank()) {
            onError(Exception("El título está vacío"))
            return
        }

        if (_description.value.isBlank()) {
            onError(Exception("La descripción está vacía"))
            return
        }

        if (_locationName.value.isBlank() || latLng == null) {
            onError(Exception("Ubicación inválida o no convertida a coordenadas"))
            return
        }

        if (_category.value.isBlank()) {
            onError(Exception("Selecciona una categoría"))
            return
        }

        val recommendation = UserRecomendation(
            id = UUID.randomUUID().toString(),
            title = _title.value,
            description = _description.value,
            locationName = _locationName.value,
            lat = latLng.first,
            lng = latLng.second,
            category = _category.value,
            imageUrl = "", // (más adelante si usas Firebase Storage)
            isPublic = _isPublic.value,
            userId = userId,
            createdAt = System.currentTimeMillis()
        )

        Log.d("🔥 CREACION", "isPublic actual: ${_isPublic.value}")
        db.collection("user_recomendaciones")
            .document(recommendation.id)
            .set(recommendation)
            .addOnSuccessListener {
                Log.d("Firestore", "✅ Recomendación guardada correctamente")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "❌ Error al guardar: ${e.message}")
                onError(e)
            }
    }

    fun listarRecomendacion (onError: (Exception) -> Unit){
        val userId = auth.currentUser?.uid ?: "debug-user"

        db.collection("user_recomendaciones")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                _userRecommendations.clear()
                for (document in result) {
                    val rec = document.toObject(UserRecomendation::class.java)
                    _userRecommendations.add(rec)
                }
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    fun eliminarRecomendacion(
        id: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val userId = auth.currentUser?.uid ?: "debug-user"
        //val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            onError(Exception("No estás logueado"))
            return
        }

        db.collection("user_recomendaciones")
            .document(id)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }

    fun editarRecomendacion(
        recomendation: UserRecomendation,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ){
        db.collection("user_recomendaciones")
            .document(recomendation.id)
            .set(recomendation) //sobrescribimos el documento
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }


    //comprobacion de los campos, para un correcto guardado de los mismos
    fun resetCampos() {
        _title.value = ""
        _description.value = ""
        _locationName.value = ""
        _latLng.value = null
        _category.value = ""
        _isPublic.value = true // o false si prefieres privado por defecto
    }

}