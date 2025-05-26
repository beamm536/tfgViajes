package com.appclass.myapplication.data.dataStore


import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    private val FAVORITES_KEY = stringSetPreferencesKey("favorite_places")

    val favoritePlaceIds: Flow<Set<String>> = context.favoritesDataStore.data
        .map { preferences ->
            preferences[FAVORITES_KEY] ?: emptySet()
        }

    suspend fun toggleFavorite(placeId: String) {
        context.favoritesDataStore.edit { preferences ->
            val current = preferences[FAVORITES_KEY]?.toMutableSet() ?: mutableSetOf()
            if (current.contains(placeId)) {
                current.remove(placeId)
            } else {
                current.add(placeId)
            }
            preferences[FAVORITES_KEY] = current
            Log.d("FAVORITOS", "✅ Guardado en DataStore: $current")
        }
    }
}