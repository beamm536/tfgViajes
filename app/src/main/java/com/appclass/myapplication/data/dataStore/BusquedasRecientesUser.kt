package com.appclass.myapplication.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "user_preferences")//extension para acceder a DataStore
class BusquedasRecientesUser (private val context: Context){

    companion object {
        private val BUSQUEDAS_KEY = stringPreferencesKey("busquedas_recientes")
    }

    // Leer el string (lista serializada)
    val busquedasRecientes: Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            preferences[BUSQUEDAS_KEY]?.split("||")?.filter { it.isNotBlank() } ?: emptyList()
        }

    // Guardar una nueva búsqueda
    suspend fun guardarBusqueda(nueva: String) {
        context.dataStore.edit { preferences ->
            val actuales = preferences[BUSQUEDAS_KEY]?.split("||")?.toMutableList() ?: mutableListOf()
            actuales.remove(nueva) //evitamos duplicados
            actuales.add(0, nueva) // lo añadimos al principio
            if (actuales.size > 5) actuales.removeAt(actuales.lastIndex)
            preferences[BUSQUEDAS_KEY] = actuales.joinToString("||")
        }
    }
}