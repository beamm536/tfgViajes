package com.appclass.myapplication.data.dataStore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.userPrefsDataStore by preferencesDataStore(name = "user_preferences")
val Context.favoritesDataStore by preferencesDataStore(name = "favorites")